package indy.pseudokod.environment;

import indy.pseudokod.exceptions.ConstantAssignmentException;
import indy.pseudokod.exceptions.DataTypeMismatchException;
import indy.pseudokod.exceptions.VariableDeclaredException;
import indy.pseudokod.exceptions.VariableNotDeclaredException;
import indy.pseudokod.runtime.values.RuntimeValue;
import indy.pseudokod.runtime.values.ValueType;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an environment in which variables are declared, assigned values, and resolved.
 * The environment supports a hierarchical structure, where each environment can have a parent
 * to resolve variables from outer scopes.<br><br>
 *
 * The environment manages variable declarations and assignments, checking for conflicts such as
 * re-declaration of variables, type mismatches, and attempts to assign to constants.
 */
public class Environment {
    private final Environment parent;
    private final Map<String, Variable> variables;

    /**
     * Constructs a new instance of {@link Environment} with the specified parent environment.
     *
     * @param parent The parent environment.
     */
    public Environment(Environment parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    /**
     * Constructs a new instance of top-level {@link Environment} with no parent.
     */
    public Environment() {
        this.parent = null;
        this.variables = new HashMap<>();
    }

    /**
     * Declares a new variable in the environment.
     *
     * @param name The name of the variable.
     * @param type The type of the variable.
     * @param constant {@code true} if the variable is constant, {@code false} if it is mutable.
     * @param value The initial value of the variable.
     * @throws VariableDeclaredException If the variable has already been declared.
     * @throws DataTypeMismatchException If there is a data type mismatch.
     */
    public void declareVariable(String name, ValueType type, boolean constant, RuntimeValue value) throws VariableDeclaredException, DataTypeMismatchException {
        if(this.variables.containsKey(name)) throw new VariableDeclaredException(name);

        Variable variable = new Variable(type, name, constant, value);
        if(variable.type() != value.type() && value.type() != ValueType.NULL) throw new DataTypeMismatchException(variable.type(), value.type());

        this.variables.put(name, variable);
    }

    /**
     * Assigns a new value to an existing variable in the environment.
     *
     * @param name The name of the variable.
     * @param value The new value to assign to the variable.
     * @return The value assigned to the variable.
     * @throws VariableNotDeclaredException If the variable is not declared in the current scope or any of its parent environments.
     * @throws ConstantAssignmentException If the variable is constant.
     * @throws DataTypeMismatchException If there is a data type mismatch.
     */
    public RuntimeValue assignVariable(String name, RuntimeValue value) throws VariableNotDeclaredException, ConstantAssignmentException, DataTypeMismatchException {
        final Environment env = this.resolveVariable(name);
        Variable variable = env.variables.get(name);

        if(variable.constant()) throw new ConstantAssignmentException(name);
        if(variable.type() != value.type() && value.type() != ValueType.NULL) throw new DataTypeMismatchException(variable.type(), value.type());
        variable.setValue(value);

        env.variables.put(name, variable);
        return value;
    }

    /**
     * Retrieves the value of a variable from the environment.
     *
     * @param name The name of the variable.
     * @return The value of the variable.
     * @throws VariableNotDeclaredException If the variable is not declared in the current scope or any of its parent environments.
     */
    public RuntimeValue getVariable(String name) throws VariableNotDeclaredException {
        final Environment env = this.resolveVariable(name);
        return env.variables.get(name).value();
    }

    /**
     * Retrieves the type of a variable from the environment.
     *
     * @param name The name of the variable.
     * @return The type of the variable.
     * @throws VariableNotDeclaredException If the variable is not declared in the current scope or any of its parent environments.
     */
    public ValueType getVariableType(String name) throws VariableNotDeclaredException {
        final Environment env = this.resolveVariable(name);
        return env.variables.get(name).type();
    }

    /**
     * Resolves a variable by checking the current scope and all of its parent environments.
     * If the variable is not found in the current scope, it will check the parent environment recursively.
     *
     * @param name The name of the variable.
     * @return The environment that contains the variable.
     * @throws VariableNotDeclaredException If the variable is not declared in the current environment or any of its parent environments.
     */
    public Environment resolveVariable(String name) throws VariableNotDeclaredException {
        if(this.variables.containsKey(name)) return this;
        if(this.parent == null) throw new VariableNotDeclaredException(name);
        return this.parent.resolveVariable(name);
    }
}

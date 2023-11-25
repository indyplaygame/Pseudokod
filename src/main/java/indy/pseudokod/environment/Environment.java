package indy.pseudokod.environment;

import indy.pseudokod.exceptions.ConstantAssignmentException;
import indy.pseudokod.exceptions.IncompatibleDataTypeException;
import indy.pseudokod.exceptions.VariableDeclaredException;
import indy.pseudokod.exceptions.VariableNotDeclaredException;
import indy.pseudokod.runtime.values.RuntimeValue;
import indy.pseudokod.runtime.values.ValueType;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Environment parent;
    private final Map<String, Variable> variables;

    public Environment(Environment parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public Environment() {
        this.variables = new HashMap<>();
    }

    public void declareVariable(String name, ValueType type, boolean constant, RuntimeValue value) throws Throwable {
        if(this.variables.containsKey(name)) throw new VariableDeclaredException(name);

        Variable variable = new Variable(type, name, constant, value);

        if(variable.type() != value.type() && value.type() != ValueType.NULL) throw new IncompatibleDataTypeException(variable.type(), value.type());

        this.variables.put(name, variable);
    }

    public RuntimeValue assignVariable(String name, RuntimeValue value) throws Throwable {
        final Environment env = this.resolveVariable(name);

        Variable variable = this.variables.get(name);

        if(variable.constant()) throw new ConstantAssignmentException(name);
        if(variable.type() != value.type() && value.type() != ValueType.NULL) throw new IncompatibleDataTypeException(variable.type(), value.type());
        variable.setValue(value);

        env.variables.put(name, variable);

        return value;
    }

    public RuntimeValue getVariable(String name) throws Throwable {
        final Environment env = this.resolveVariable(name);
        return env.variables.get(name).value();
    }

    public ValueType getVariableType(String name) throws Throwable {
        final Environment env = this.resolveVariable(name);
        return env.variables.get(name).type();
    }

    public Environment resolveVariable(String name) throws Throwable {
        if(this.variables.containsKey(name)) return this;
        if(this.parent == null) throw new VariableNotDeclaredException(name);
        return this.parent.resolveVariable(name);
    }
}

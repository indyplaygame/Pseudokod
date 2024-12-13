package indy.pseudokod.runtime.values;

import indy.pseudokod.ast.Statement;
import indy.pseudokod.environment.Environment;

import java.util.List;
import java.util.Map;

/**
 * Represents a function in the runtime environment as a {@link RuntimeValue} with type {@link ValueType#Function}.
 */
public class Function extends RuntimeValue {
    private final List<String> parameters;
    private final Map<String, ValueType> parameter_types;
    private final List<String> variables;
    private final Map<String, ValueType> variable_types;
    private final List<Statement> body;
    private final Environment env;

    /**
     * Constructs a new instance of {@link Function} with the specified name, parameters, variables, body and environment.
     * @param parameters The list of parameter names.
     * @param parameter_types A map of parameter names to their corresponding types.
     * @param variables The list of variable names used inside the function.
     * @param variables_types A map of variable names to their corresponding types.
     * @param body The list of statements representing the body of the function.
     * @param env The {@link Environment} in which the function is defined.
     */
    public Function(List<String> parameters, Map<String, ValueType> parameter_types, List<String> variables, Map<String, ValueType> variables_types, List<Statement> body, Environment env) {
        super(ValueType.Function);
        this.parameters = parameters;
        this.body = body;
        this.env = env;
        this.parameter_types = parameter_types;
        this.variables = variables;
        this.variable_types = variables_types;
    }

    /**
     * @return The list of parameter names of this {@link Function}.
     */
    public List<String> parameters() {
        return this.parameters;
    }

    /**
     * @return A map of parameter names to their corresponding types.
     */
    public Map<String, ValueType> parameter_types() {
        return this.parameter_types;
    }

    /**
     * @return The list of names of variables used in this {@link Function}.
     */
    public List<String> variables() {
        return this.variables;
    }

    /**
     * @return A map of variable names to their corresponding types.
     */
    public Map<String, ValueType> variable_types() {
        return this.variable_types;
    }

    /**
     * @return The {@link Environment} in which this {@link Function} is defined.
     */
    public Environment env() {
        return this.env;
    }

    /**
     * @return The body of this {@link Function}, represented by a list of {@link Statement} nodes.
     */
    public List<Statement> body() {
        return this.body;
    }
}

package indy.pseudokod.runtime.values;

import indy.pseudokod.ast.Statement;
import indy.pseudokod.environment.Environment;

import java.util.List;
import java.util.Map;

public class Function extends RuntimeValue {

    private final String name;
    private final List<String> parameters;
    private final Map<String, ValueType> parameter_types;
    private final List<String> variables;
    private final Map<String, ValueType> variable_types;
    private final List<Statement> body;
    private final Environment env;

    public Function(String name, List<String> parameters, Map<String, ValueType> parameter_types, List<String> variables, Map<String, ValueType> variables_types, List<Statement> body, Environment env) {
        super(ValueType.Function);
        this.name = name;
        this.parameters = parameters;
        this.body = body;
        this.env = env;
        this.parameter_types = parameter_types;
        this.variables = variables;
        this.variable_types = variables_types;
    }

    public List<String> parameters() {
        return this.parameters;
    }

    public Map<String, ValueType> parameter_types() {
        return this.parameter_types;
    }

    public List<String> variables() {
        return this.variables;
    }

    public Map<String, ValueType> variable_types() {
        return this.variable_types;
    }

    public Environment env() {
        return this.env;
    }

    public List<Statement> body() {
        return this.body;
    }
}

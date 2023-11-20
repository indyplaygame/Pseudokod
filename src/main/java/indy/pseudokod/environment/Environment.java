package indy.pseudokod.environment;

import indy.pseudokod.exceptions.VariableDeclaredException;
import indy.pseudokod.exceptions.VariableNotDeclaredException;
import indy.pseudokod.runtime.values.RuntimeValue;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Environment parent;
    private Map<String, RuntimeValue> variables;

    public Environment(Environment parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public Environment() {
        this.variables = new HashMap<>();
    }

    public void declareVariable(String name, RuntimeValue value) throws Throwable {
        if(this.variables.containsKey(name)) throw new VariableDeclaredException(name);
        this.variables.put(name, value);
    }

    public RuntimeValue assignVariable(String name, RuntimeValue value) throws Throwable {
        final Environment env = this.resolveVariable(name);
        env.variables.put(name, value);

        return value;
    }

    public RuntimeValue getVariable(String name) throws Throwable {
        final Environment env = this.resolveVariable(name);
        return env.variables.get(name);
    }

    public Environment resolveVariable(String name) throws Throwable {
        if(this.variables.containsKey(name)) return this;
        if(this.parent == null) throw new VariableNotDeclaredException(name);
        return this.parent.resolveVariable(name);
    }
}

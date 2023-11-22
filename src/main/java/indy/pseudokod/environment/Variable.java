package indy.pseudokod.environment;

import indy.pseudokod.runtime.values.RuntimeValue;
import indy.pseudokod.runtime.values.ValueType;

public class Variable {
    private final ValueType type;
    private final String name;
    private final boolean constant;

    private RuntimeValue value;

    public Variable(ValueType type, String name, boolean constant, RuntimeValue value) {
        this.type = type;
        this.name = name;
        this.constant = constant;
        this.value = value;
    }

    public ValueType type() {
        return this.type;
    }

    public String name() {
        return this.name;
    }

    public boolean constant() {
        return this.constant;
    }

    public RuntimeValue value() {
        return this.value;
    }

    public void setValue(RuntimeValue value) {
        this.value = value;
    }
}

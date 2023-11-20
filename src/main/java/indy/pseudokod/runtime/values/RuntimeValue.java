package indy.pseudokod.runtime.values;

public class RuntimeValue {
    private ValueType type;

    public RuntimeValue(ValueType type) {
        this.type = type;
    }

    public ValueType type() {
        return this.type;
    }
}

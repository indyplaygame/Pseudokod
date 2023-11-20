package indy.pseudokod.runtime.values;

public class BooleanValue extends RuntimeValue {
    private boolean value;

    public BooleanValue(boolean value) {
        super(ValueType.Boolean);
        this.value = value;
    }

    public boolean value() {
        return this.value;
    }
}

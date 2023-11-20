package indy.pseudokod.runtime.values;

public class NullValue extends RuntimeValue{
    String value;

    public NullValue() {
        super(ValueType.NULL);
        this.value = null;
    }

    public String value() {
        return this.value;
    }
}

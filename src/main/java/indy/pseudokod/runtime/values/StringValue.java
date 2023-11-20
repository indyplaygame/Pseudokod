package indy.pseudokod.runtime.values;

public class StringValue extends RuntimeValue{
    private String value;

    public StringValue(String value) {
        super(ValueType.String);
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}

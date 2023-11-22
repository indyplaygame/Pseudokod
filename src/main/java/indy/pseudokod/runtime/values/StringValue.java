package indy.pseudokod.runtime.values;

import indy.pseudokod.exceptions.InvalidConversionDataTypeException;

public class StringValue extends RuntimeValue{
    private String value;

    public StringValue(String value) {
        super(ValueType.String);
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static StringValue valueOf(RuntimeValue value) throws Throwable {
        if(value.type().equals(ValueType.Number)) return new StringValue(String.valueOf(((NumberValue) value).value()));
        else if(value.type().equals(ValueType.Char)) return new StringValue(String.valueOf(((CharValue) value).value()));
        else if(value.type().equals(ValueType.Boolean)) return new StringValue(String.valueOf(((BooleanValue) value).value()));
        else if(value.type().equals(ValueType.String)) return  (StringValue) value;
        else throw new InvalidConversionDataTypeException(value.type(), ValueType.String);
    }
}

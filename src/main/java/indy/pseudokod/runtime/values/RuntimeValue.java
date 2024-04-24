package indy.pseudokod.runtime.values;

import indy.pseudokod.exceptions.IncompatibleDataTypeException;

public class RuntimeValue {
    private final ValueType type;

    public RuntimeValue(ValueType type) {
        this.type = type;
    }


    public ValueType type() {
        return this.type;
    }

    public static boolean compare(RuntimeValue value, RuntimeValue value1) throws Throwable {
        if(value.type() != value1.type() && !(value.type().equals(ValueType.NULL) || value1.type().equals(ValueType.NULL))) throw new IncompatibleDataTypeException(value.type(), value1.type(), 0);

        return StringValue.valueOf(value).value().equals(StringValue.valueOf(value1).value());
    }
}

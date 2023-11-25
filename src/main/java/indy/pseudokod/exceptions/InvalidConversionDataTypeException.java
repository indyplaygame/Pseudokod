package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

public class InvalidConversionDataTypeException extends Exception {
    public InvalidConversionDataTypeException(ValueType received, ValueType type) {
        super(received + " cannot be converted to " + type + ".");
    }
}

package indy.pseudokod.exceptions;

import com.sun.jdi.Value;
import indy.pseudokod.runtime.values.ValueType;

public class InvalidConversionDataTypeException extends Exception {
    public InvalidConversionDataTypeException(ValueType received, ValueType type) {
        super(received + " cannot be converted to " + type);
    }
}

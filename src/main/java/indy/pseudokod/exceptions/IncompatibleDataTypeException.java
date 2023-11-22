package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

public class IncompatibleDataTypeException extends Exception {
    public IncompatibleDataTypeException(ValueType type, ValueType found) {
        super("Data type '" + type + "' was expected here, but received '" + found + "'.");
    }
}

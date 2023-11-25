package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

public class InvalidCallableException extends Exception {
    public InvalidCallableException(ValueType type) {
        super(type + " is not callable data type.");
    }
}

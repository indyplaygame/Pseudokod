package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

public class InvalidWhileLoopExpressionException extends Exception {
    public InvalidWhileLoopExpressionException(ValueType type) {
        super("While loop expression is expected to return boolean value, but returned " + type.toString().toLowerCase() + " instead.");
    }
}

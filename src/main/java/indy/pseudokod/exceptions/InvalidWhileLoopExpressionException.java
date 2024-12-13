package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

/**
 * Thrown when the expression in a while loop does not evaluate to a boolean value.
 */
public class InvalidWhileLoopExpressionException extends Exception {
    /**
     * Constructs a new instance of {@link InvalidWhileLoopExpressionException}.
     *
     * @param type The type of value that was incorrectly returned in the while loop expression.
     */
    public InvalidWhileLoopExpressionException(ValueType type) {
        super("While loop expression is expected to return boolean value, but returned " + type.toString().toLowerCase() + " instead.");
    }
}

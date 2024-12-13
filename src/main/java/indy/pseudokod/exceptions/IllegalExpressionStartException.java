package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

/**
 * Thrown when the start of an expression does not match the expected type.
 */
public class IllegalExpressionStartException extends Exception {
    /**
     * Constructs a new instance of {@link IllegalExpressionStartException}.
     *
     * @param type The expected data type for the start of the expression.
     */
    public IllegalExpressionStartException(ValueType type) {
        super("Illegal start of expression, expected '" + type + "'.");
    }
}

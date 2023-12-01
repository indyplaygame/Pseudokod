package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

public class IllegalExpressionStartException extends Exception {
    public IllegalExpressionStartException(ValueType type) {
        super("Illegal start of expression, expected '" + type + "'.");
    }
}

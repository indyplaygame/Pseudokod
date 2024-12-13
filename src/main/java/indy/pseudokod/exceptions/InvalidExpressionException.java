package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

/**
 * Thrown when an expression is expected to be of a specific type, but the actual type received is incompatible or incorrect.
 */
public class InvalidExpressionException extends Exception {
    /**
     * Constructs a new instance of {@link InvalidExpressionException}.
     *
     * @param type The expected type of the expression, represented by {@link NodeType}.
     * @param received The received type of the expression, represented by {@link NodeType}.
     */
    public InvalidExpressionException(NodeType type, NodeType received) {
        super("Expected '" + type + "' expression, but received '" + received + "'.");
    }
}

package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

public class MissingExpressionException extends Exception {
    public MissingExpressionException(NodeType type, NodeType received) {
        super("Expected '" + type + "' expression, but received '" + received + "'.");
    }
}

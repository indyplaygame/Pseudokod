package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

public class UnexpectedNodeException extends Exception {
    public UnexpectedNodeException(NodeType type) {
        super("Node " + type + " was not expected here.");
    }
}

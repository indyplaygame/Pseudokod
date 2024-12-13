package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

/**
 * Thrown when an unexpected node type is encountered during parsing or interpretation.
 */
public class UnexpectedNodeException extends Exception {
    /**
     * Constructs a new instance of {@link UnexpectedNodeException}.
     *
     * @param type The type of the node that was unexpectedly encountered, represented by {@link NodeType}.
     */
    public UnexpectedNodeException(NodeType type) {
        super("Node " + type + " was not expected here.");
    }
}
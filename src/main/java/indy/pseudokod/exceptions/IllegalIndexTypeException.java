package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

/**
 * Thrown when an invalid type is used as an index for an array.
 */
public class IllegalIndexTypeException extends Exception {
    /**
     * Constructs a new instance of {@link IllegalIndexTypeException}.
     *
     * @param type The actual type, represented by {@link NodeType}, received for the list index.
     */
    public IllegalIndexTypeException(NodeType type) {
        super("Identifier or NumericLiteral expected as list index, received '" + type.name() + "'.");
    }
}

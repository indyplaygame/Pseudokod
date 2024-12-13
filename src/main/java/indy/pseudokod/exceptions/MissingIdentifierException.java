package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;
import indy.pseudokod.ast.Statement;

/**
 * Thrown when an identifier should have been found in the given statement context,
 * but the actual statement does not provide one.
 */
public class MissingIdentifierException extends Exception {
    /**
     * Constructs a new instance of {@link MissingIdentifierException}.
     *
     * @param type The expected node type where an identifier is required.
     * @param statement The statement in which the identifier was expected.
     */
    public MissingIdentifierException(NodeType type, Statement statement) {
        super("Identifier expected in '" + type + "', but found: " + statement.kind() + ".");
    }
}

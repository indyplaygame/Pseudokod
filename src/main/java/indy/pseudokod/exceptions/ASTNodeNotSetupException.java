package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

/**
 * Thrown when an abstract syntax tree (AST) node has not been properly set up for interpretation.
 */
public class ASTNodeNotSetupException extends Exception {
    /**
     * Constructs a new instance of {@link ASTNodeNotSetupException}.
     *
     * @param type The type of the AST node that is not set up.
     */
    public ASTNodeNotSetupException(NodeType type) {
        super("This AST node has not yet been setup for interpretation: " + type + ".");
    }
}

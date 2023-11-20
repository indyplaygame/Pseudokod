package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

public class ASTNodeNotSetupException extends Exception {
    public ASTNodeNotSetupException(NodeType type) {
        super("This AST node has not yet been setup for interpretation: " + type + ".");
    }
}

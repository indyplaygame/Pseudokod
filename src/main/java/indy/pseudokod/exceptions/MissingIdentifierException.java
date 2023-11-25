package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;
import indy.pseudokod.ast.Statement;

public class MissingIdentifierException extends Exception {
    public MissingIdentifierException(NodeType type, Statement statement) {
        super("Identifier expected in '" + type + "', but found: " + statement.kind() + ".");
    }
}

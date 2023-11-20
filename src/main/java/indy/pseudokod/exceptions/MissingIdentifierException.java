package indy.pseudokod.exceptions;

import indy.pseudokod.ast.Statement;

public class MissingIdentifierException extends Exception {
    public MissingIdentifierException(Statement statement) {
        super("Identifier expected in AssignmentExpression, but found: " + statement.kind() + ".");
    }
}

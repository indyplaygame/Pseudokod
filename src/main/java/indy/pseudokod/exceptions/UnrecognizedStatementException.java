package indy.pseudokod.exceptions;

import indy.pseudokod.ast.Statement;

public class UnrecognizedStatementException extends Exception {
    public UnrecognizedStatementException(Statement statement) {
        super("Statement '" + statement.kind() + "' is unrecognizable and cannot be parsed.");
    }
}

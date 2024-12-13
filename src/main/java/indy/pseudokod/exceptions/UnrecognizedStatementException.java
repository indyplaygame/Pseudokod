package indy.pseudokod.exceptions;

import indy.pseudokod.ast.Statement;

/**
 * Thrown when a statement is encountered that cannot be recognized and parsed.
 */
public class UnrecognizedStatementException extends Exception {
    /**
     * Constructs a new instance of {@link UnrecognizedStatementException}.
     *
     * @param statement The unrecognized statement that caused the exception.
     */
    public UnrecognizedStatementException(Statement statement) {
        super("Statement '" + statement.kind() + "' is unrecognizable and cannot be parsed.");
    }
}

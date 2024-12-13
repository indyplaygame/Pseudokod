package indy.pseudokod.exceptions;

import indy.pseudokod.lexer.Token;

/**
 * Thrown when the parser has found a token that is not valid
 * or expected at a specific point in the source code.
 */
public class UnexpectedTokenException extends Exception {
    /**
     * Constructs a new instance of {@link UnexpectedTokenException}.
     *
     * @param token The {@link Token} that was encountered unexpectedly during parsing.
     */
    public UnexpectedTokenException(Token token) {
        super("Unexpected token found during parsing: {value: \"" + token.value() + "\", type: " + token.type() + "} (Line " + token.line() + ").");
    }
}

package indy.pseudokod.exceptions;

import indy.pseudokod.lexer.Token;

public class UnexpectedTokenException extends Exception {
    public UnexpectedTokenException(Token token) {
        super("Unexpected token found during parsing: {value: \"" + token.value() + "\", type: " + token.type() + "}.");
    }
}

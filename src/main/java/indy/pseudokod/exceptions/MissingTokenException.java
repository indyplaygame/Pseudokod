package indy.pseudokod.exceptions;

import indy.pseudokod.lexer.TokenType;

public class MissingTokenException extends Exception {
    public MissingTokenException(TokenType type, TokenType found) {
        super("Unexpected token found. Expected \'" + type.name() + "\' token, found \'" + found.name() + "\'.");
    }

    public MissingTokenException(TokenType type) {
        super("Expected \'" + type.name() + "\' token and it was not found.");
    }
}

package indy.pseudokod.exceptions;

import indy.pseudokod.lexer.TokenType;

public class MissingTokenException extends Exception {
    public MissingTokenException(TokenType type, TokenType found) {
        super("Unexpected token found. Expected '" + type.name() + "' token, found '" + found.name() + "'.");
    }

    public MissingTokenException(TokenType type) {
        super("Expected '" + type.name() + "' token and it was not found.");
    }

    public MissingTokenException(TokenType received, TokenType... types) {
        super(createMessage(received, types));
    }

    private static String createMessage(TokenType received, TokenType... types) {
        StringBuilder message = new StringBuilder("Expected one of the following tokens: ");
        for(TokenType type : types) {
            message.append(type).append(", ");
        }
        message.append("but received ").append(received).append(" token.");
        return message.toString();
    }
}

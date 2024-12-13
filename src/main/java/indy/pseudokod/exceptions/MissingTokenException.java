package indy.pseudokod.exceptions;

import indy.pseudokod.lexer.TokenType;

/**
 * Thrown when the expected token type was not found, or an unexpected token was found where a specific token type was required.
 */
public class MissingTokenException extends Exception {
    /**
     * Constructs a new instance of {@link MissingTokenException} when an unexpected token is found.
     *
     * @param type The expected token type, represented by {@link TokenType}.
     * @param found The token type, represented by {@link TokenType}, that was found instead of the expected type.
     */
    public MissingTokenException(TokenType type, TokenType found) {
        super("Unexpected token found. Expected '" + type.name() + "' token, found '" + found.name() + "'.");
    }

    /**
     * Constructs a new instance of {@link MissingTokenException} when a specific token type was expected,
     * but it was not found.
     *
     * @param type The expected token type, represented by {@link TokenType}, that was not found.
     */
    public MissingTokenException(TokenType type) {
        super("Expected '" + type.name() + "' token and it was not found.");
    }

    /**
     * Constructs a new instance of {@link MissingTokenException} when one of several token types was expected,
     * but an incorrect token was received.
     *
     * @param received The token type that was received, represented by {@link TokenType}.
     * @param types The list of expected token types.
     */
    public MissingTokenException(TokenType received, TokenType... types) {
        super(createMessage(received, types));
    }

    /**
     * Creates a detailed message for the exception when multiple expected token types are provided.
     *
     * @param received The token type that was received, represented by {@link TokenType}.
     * @param types The list of expected token types.
     * @return The generated exception message.
     */
    private static String createMessage(TokenType received, TokenType... types) {
        StringBuilder message = new StringBuilder("Expected one of the following tokens: ");
        for(TokenType type : types) {
            message.append(type).append(", ");
        }
        message.append("but received ").append(received).append(" token instead.");
        return message.toString();
    }
}

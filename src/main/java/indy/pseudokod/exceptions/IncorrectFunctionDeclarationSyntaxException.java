package indy.pseudokod.exceptions;

import indy.pseudokod.lexer.TokenType;

/**
 * Thrown when the syntax of a function declaration is incorrect due to a token that is out of place.
 */
public class IncorrectFunctionDeclarationSyntaxException extends Exception {
    /**
     * Constructs a new instance of {@link IncorrectFunctionDeclarationSyntaxException}.
     *
     * @param type The type of token, represented by {@link TokenType}, that caused the syntax error in the function declaration.
     */
    public IncorrectFunctionDeclarationSyntaxException(TokenType type) {
        super("Unexpected token found inside the function declaration statement: " + type + ".");
    }
}

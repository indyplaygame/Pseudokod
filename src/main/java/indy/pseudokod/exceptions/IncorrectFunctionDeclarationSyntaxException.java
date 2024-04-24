package indy.pseudokod.exceptions;

import indy.pseudokod.lexer.TokenType;

public class IncorrectFunctionDeclarationSyntaxException extends Exception {
    public IncorrectFunctionDeclarationSyntaxException(TokenType type) {
        super("Unexpected token found inside function declaration statement: " + type + ".");
    }
}

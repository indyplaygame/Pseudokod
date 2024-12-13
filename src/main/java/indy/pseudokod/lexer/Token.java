package indy.pseudokod.lexer;

/**
 * Represents a token produced during the lexical analysis phase of parsing.
 *
 * @param value A textual value of the token.
 * @param type A {@link TokenType} which categorizes the token's role in the source code.
 * @param line The line number where the token appears in the source code, aiding in error reporting and debugging.
 */
public record Token(String value, TokenType type, int line) {}
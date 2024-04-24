package indy.pseudokod.lexer;

public class Token {
    private String value = null;
    private TokenType type = null;
    private final int line;

    public Token(String value, TokenType type, int line) {
        this.value = value;
        this.type = type;
        this.line = line;
    }

    public String value() {
        return this.value;
    }

    public TokenType type() {
        return this.type;
    }

    public int line() {
        return this.line;
    }
}

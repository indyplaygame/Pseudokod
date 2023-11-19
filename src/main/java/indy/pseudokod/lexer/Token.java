package indy.pseudokod.lexer;

public class Token {
    private String value = null;
    private TokenType type = null;

    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public String value() {
        return this.value;
    }

    public TokenType type() {
        return this.type;
    }
}

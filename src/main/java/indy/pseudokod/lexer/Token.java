package indy.pseudokod.lexer;

enum TokenType {
    Number,
    Identifier,
    Assignment,
    InRange,
    OpenParenthesis,
    CloseParenthesis,
    BinaryOperator,
    DivOperator,
    ModulusOperator,
    DataToken,
    DataType,
    Equal,
    NotEqual,
    LessThan,
    LessOrEqual,
    GreaterThan,
    GreaterOrEqual,
    OrOperator,
    AndOperator,
    NotOperator,
    Tabulator,
    NewLine,
    Colon,
    Semicolon,
    Comma,
    NumberType,
    CharType,
    StringType,
    BooleanType,
    ListType,
    RangeType,
    EndOfFile
}

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

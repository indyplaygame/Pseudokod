package indy.pseudokod.lexer;

/**
 * Represents the various types of tokens that can be recognized during the lexical analysis of source code.
 * These token types are used by the lexer to categorize the raw characters from the source code into
 * meaningful units for the parser.
 */
public enum TokenType {
    Number,
    Character,
    Text,
    Range,
    Identifier,
    Assignment,
    InRange,
    OpenParenthesis,
    CloseParenthesis,
    OpenBracket,
    CloseBracket,
    OpenBrace,
    CloseBrace,
    BinaryOperator,
    DivOperator,
    ModulusOperator,
    Equals,
    ComparisonOperator,
    LogicalOperator,
    BitwiseOperator,
    ShiftOperator,
    DataToken,
    ResultToken,
    ReturnToken,
    ImportToken,
    DataType,
    Indent,
    NewLine,
    Colon,
    Semicolon,
    Comma,
    Dot,
    Quote,
    Apostrophe,
    PrintFunction,
    GetFunction,
    IfStatement,
    ElseStatement,
    ForStatement,
    WhileStatement,
    DoStatement,
    Function,
    Null,
    EndOfFile
}

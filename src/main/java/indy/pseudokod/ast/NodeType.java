package indy.pseudokod.ast;

/**
 * Enum representing the different types of nodes in the Abstract Syntax Tree (AST).<br><br>
 *
 * The AST is a tree representation of the structure of the source code, where each node represents a construct
 * from the source code such as expressions, literals, statements, and declarations.
 * This enum categorizes the various node types that can exist within the AST during the interpretation or execution
 * of the code.
 */
public enum NodeType {
    BinaryExpression,
    ComparisonExpression,
    LogicalExpression,
    BitwiseExpression,
    Identifier,
    NumericLiteral,
    CharacterLiteral,
    StringLiteral,
    BooleanLiteral,
    ArrayLiteral,
    SetLiteral,
    RangeLiteral,
    Program,
    DataDeclaration,
    FunctionDeclaration,
    VariableDeclaration,
    AssignmentExpression,
    IndexExpression,
    CallExpression,
    EllipsisStatement,
    PrintFunction,
    GetFunction,
    IfStatement,
    ElseStatement,
    ForStatement,
    WhileStatement,
    ReturnStatement,
    ImportStatement
}

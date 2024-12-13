package indy.pseudokod.ast;

/**
 * Represents an expression in the abstract syntax tree (AST).<br><br>
 *
 * An expression is a construct that computes and returns a value.
 * It can represent constants, variables, operations, or function calls.
 * Expressions are often used within statements but differ from statements
 * as they always produce a value, whereas statements may not.<br><br>
 *
 * This class serves as the base for all expression nodes.
 */
public class Expression extends Statement {
    /**
     * Constructs a new instance of {@link Expression} with the specified {@link NodeType}.
     *
     * @param kind The specific type of the expression, as defined by {@link NodeType}.
     */
    public Expression(NodeType kind) {
        super(kind);
    }
}

package indy.pseudokod.ast;

/**
 * Represents a logical expression in the abstract syntax tree (AST).
 * A logical expression consists of two expressions and an operator.
 */
public class LogicalExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    /**
     * Constructs a new instance of {@link LogicalExpression} with two operands.
     *
     * @param left The left operand of the logical expression.
     * @param right The right operand of the logical expression.
     * @param operator The logical operator (NOT, OR, AND, XOR).
     */
    public LogicalExpression(Expression left, Expression right, String operator) {
        super(NodeType.LogicalExpression);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * Constructs a new instance of {@link LogicalExpression} with single operands.
     *
     * @param right The operand of the logical expression.
     * @param operator The logical operator (NOT, OR, AND, XOR).
     */
    public LogicalExpression(Expression right, String operator) {
        super(NodeType.LogicalExpression);
        this.left = null;
        this.right = right;
        this.operator = operator;
    }

    /**
     * @return The left operand of the {@link LogicalExpression}.
     */
    public Expression left() {
        return this.left;
    }

    /**
     * @return The right operand of the {@link LogicalExpression}.
     */
    public Expression right() {
        return this.right;
    }

    /**
     * @return The logical operator of the {@link LogicalExpression}.
     */
    public String operator() {
        return this.operator;
    }
}

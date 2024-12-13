package indy.pseudokod.ast;

/**
 * Represents a binary expression in the abstract syntax tree (AST).
 * A binary expression consists of two expressions and an operator.
 */
public class BinaryExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    /**
     * Constructs a new instance of {@link BinaryExpression}.
     *
     * @param left The left operand of the binary operation.
     * @param right The right operand of the binary operation.
     * @param operator The operator of the binary operation (+, -, *, /, %, //).
     */
    public BinaryExpression(Expression left, Expression right, String operator) {
        super(NodeType.BinaryExpression);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * @return The {@link Expression} on the left-hand side of the binary operation.
     */
    public Expression left() {
        return this.left;
    }

    /**
     * @return The {@link Expression} on the right-hand of the binary operation.
     */
    public Expression right() {
        return this.right;
    }

    /**
     * @return The operator of the binary operation.
     */
    public String operator() {
        return this.operator;
    }
}

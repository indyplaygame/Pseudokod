package indy.pseudokod.ast;

/**
 * Represents a comparison expression in the abstract syntax tree (AST).
 * A comparison expression consists of two expressions and an operator.
 */
public class ComparisonExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    /**
     * Constructs a new instance of {@link ComparisonExpression}.
     *
     * @param left The left-hand side expression of the comparison.
     * @param right The right-hand side expression of the comparison.
     * @param operator The comparison operator (==, !=, <, >, <=, >=).
     */
    public ComparisonExpression(Expression left, Expression right, String operator) {
        super(NodeType.ComparisonExpression);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * @return The left-hand side {@link Expression} of the {@link ComparisonExpression}.
     */
    public Expression left() {
        return this.left;
    }

    /**
     * @return The right-hand side {@link Expression} of the {@link ComparisonExpression}.
     */
    public Expression right() {
        return this.right;
    }

    /**
     * @return The operator of the {@link ComparisonExpression}.
     */
    public String operator() {
        return this.operator;
    }
}

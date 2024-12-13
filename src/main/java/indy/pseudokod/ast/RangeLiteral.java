package indy.pseudokod.ast;

/**
 * Represents a range literal in the abstract syntax tree (AST).
 * A range literal is used to define a range of values, inclusive or exclusive of the bounds.
 */
public class RangeLiteral extends Expression {
    private final boolean left_inclusive;
    private final Expression left_bound;
    private final Expression right_bound;
    private final boolean right_inclusive;

    /**
     * Constructs a new instance of {@link RangeLiteral}.
     *
     * @param left_bound The expression representing the lower bound of the range.
     * @param right_bound The expression representing the upper bound of the range.
     * @param left_inclusive Determines whether the lower bound is inclusive or exclusive.
     * @param right_inclusive Determines whether the upper bound is inclusive or exclusive.
     */
    public RangeLiteral(Expression left_bound, Expression right_bound, boolean left_inclusive, boolean right_inclusive) {
        super(NodeType.RangeLiteral);
        this.left_inclusive = left_inclusive;
        this.left_bound = left_bound;
        this.right_bound = right_bound;
        this.right_inclusive = right_inclusive;
    }

    /**
     * Returns whether the lower bound is inclusive or exclusive.
     *
     * @return {@code true} if the lower bound is inclusive, {@code false} otherwise.
     */
    public boolean leftIncluded() {
        return this.left_inclusive;
    }

    /**
     * @return The {@link Expression} representing the lower bound of the range.
     */
    public Expression leftBound() {
        return this.left_bound;
    }

    /**
     * @return The {@link Expression} representing the upper bound of the range.
     */
    public Expression rightBound() {
        return this.right_bound;
    }

    /**
     * Returns whether the upper bound is inclusive or exclusive.
     *
     * @return {@code true} if the upper bound is inclusive, {@code false} otherwise.
     */
    public boolean rightIncluded() {
        return this.right_inclusive;
    }
}

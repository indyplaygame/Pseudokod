package indy.pseudokod.ast;

import java.util.function.Function;

public class RangeLiteral extends Expression {

    private final boolean left_inclusive;
    private final Expression left_bound;
    private final Expression right_bound;
    private final boolean right_inclusive;

    public RangeLiteral(Expression left_bound, Expression right_bound, boolean left_inclusive, boolean right_inclusive) {
        super(NodeType.RangeLiteral);
        this.left_inclusive = left_inclusive;
        this.left_bound = left_bound;
        this.right_bound = right_bound;
        this.right_inclusive = right_inclusive;
    }

    public boolean left_included() {
        return this.left_inclusive;
    }

    public Expression left_bound() {
        return this.left_bound;
    }

    public Expression right_bound() {
        return this.right_bound;
    }

    public boolean right_included() {
        return this.right_inclusive;
    }
}

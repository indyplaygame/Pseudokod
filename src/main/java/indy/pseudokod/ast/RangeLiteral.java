package indy.pseudokod.ast;

public class RangeLiteral extends Expression {

    private final boolean left_included;
    private final Expression left_bound;
    private final Expression right_bound;
    private final boolean right_included;

    public RangeLiteral(Expression left_bound, Expression right_bound, boolean left_included, boolean right_included) {
        super(NodeType.RangeLiteral);
        this.left_included = left_included;
        this.left_bound = left_bound;
        this.right_bound = right_bound;
        this.right_included = right_included;
    }

    public boolean left_included() {
        return this.left_included;
    }

    public Expression left_bound() {
        return this.left_bound;
    }

    public Expression right_bound() {
        return this.right_bound;
    }

    public boolean right_included() {
        return this.right_included;
    }
}

package indy.pseudokod.ast;

public class BitwiseExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    public BitwiseExpression(Expression left, Expression right, String operator) {
        super(NodeType.BitwiseExpression);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public BitwiseExpression(Expression right, String operator) {
        super(NodeType.BitwiseExpression);
        this.left = null;
        this.right = right;
        this.operator = operator;
    }

    public Expression left() {
        return this.left;
    }

    public Expression right() {
        return this.right;
    }

    public String operator() {
        return this.operator;
    }
}

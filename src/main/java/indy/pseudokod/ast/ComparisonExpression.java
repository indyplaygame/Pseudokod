package indy.pseudokod.ast;

public class ComparisonExpression extends Expression {
    Expression left;
    Expression right;
    String operator;

    public ComparisonExpression(Expression left, Expression right, String operator) {
        super(NodeType.ComparisonExpression);
        this.left = left;
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

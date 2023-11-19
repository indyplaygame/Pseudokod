package indy.pseudokod.ast;

public class BinaryExpression extends Expression {
    NodeType kind;
    Expression left;
    Expression right;
    String operator;

    public BinaryExpression(Expression left, Expression right, String operator) {
        this.kind = NodeType.BinaryExpression;
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public NodeType kind() {
        return this.kind;
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

package indy.pseudokod.ast;

public class LogicalExpression extends Expression {
    Expression left;
    Expression right;
    String operator;

    public LogicalExpression(Expression left, Expression right, String operator) {
        super(NodeType.LogicalExpression);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public LogicalExpression(Expression right, String operator) {
        super(NodeType.LogicalExpression);
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

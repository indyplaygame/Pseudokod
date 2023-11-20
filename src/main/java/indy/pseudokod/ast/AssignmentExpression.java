package indy.pseudokod.ast;

public class AssignmentExpression extends Expression {
    Expression expression;
    Expression value;

    public AssignmentExpression(Expression expression, Expression value) {
        super(NodeType.AssignmentExpression);
        this.expression = expression;
        this.value = value;
    }

    public Expression expression() {
        return this.expression;
    }

    public Expression value() {
        return this.value;
    }
}

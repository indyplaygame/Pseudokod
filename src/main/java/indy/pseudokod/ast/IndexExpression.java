package indy.pseudokod.ast;

public class IndexExpression extends Expression {

    Expression array;
    Expression index;

    public IndexExpression(Expression array, Expression index) {
        super(NodeType.IndexExpression);
        this.array = array;
        this.index = index;
    }

    public Expression array() {
        return this.array;
    }

    public Expression index() {
        return this.index;
    }
}

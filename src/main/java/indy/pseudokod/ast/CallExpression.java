package indy.pseudokod.ast;

import java.util.List;

public class CallExpression extends Expression {

    private final Expression expression;
    private final List<Expression> args;

    public CallExpression(Expression expression, List<Expression> args) {
        super(NodeType.CallExpression);
        this.expression = expression;
        this.args = args;
    }

    public Expression expression() {
        return this.expression;
    }

    public List<Expression> args() {
        return this.args;
    }
}

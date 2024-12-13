package indy.pseudokod.ast;

import java.util.List;

/**
 * Represents a function call expression in the abstract syntax tree (AST).
 */
public class CallExpression extends Expression {
    private final Expression expression;
    private final List<Expression> args;

    /**
     * Constructs a new instance of {@link CallExpression}.
     *
     * @param expression The expression representing the function to be called.
     * @param args The list of arguments passed to the function.
     */
    public CallExpression(Expression expression, List<Expression> args) {
        super(NodeType.CallExpression);
        this.expression = expression;
        this.args = args;
    }

    /**
     * @return The {@link Expression} representing the function to be called.
     */
    public Expression expression() {
        return this.expression;
    }

    /**
     * @return The list of arguments passed to the function.
     */
    public List<Expression> args() {
        return this.args;
    }
}

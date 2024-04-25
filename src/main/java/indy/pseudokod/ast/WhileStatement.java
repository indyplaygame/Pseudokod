package indy.pseudokod.ast;

import java.util.List;

public class WhileStatement extends Statement {
    private final Expression expression;
    private final List<Statement> body;
    private final boolean do_while;

    public WhileStatement(Expression expression, List<Statement> body, boolean do_while) {
        super(NodeType.WhileStatement);
        this.expression = expression;
        this.body = body;
        this.do_while = do_while;
    }

    public Expression expression() {
        return this.expression;
    }

    public List<Statement> body() {
        return this.body;
    }

    public boolean isDoWhile() {
        return this.do_while;
    }
}

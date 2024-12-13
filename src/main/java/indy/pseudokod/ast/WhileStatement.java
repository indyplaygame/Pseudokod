package indy.pseudokod.ast;

import java.util.List;

/**
 * Represents a while or do-while loop in the abstract syntax tree (AST).
 * A while loop repeatedly executes a block of statements as long as a specified condition is {@code true}.
 * This class supports both the traditional {@code while} loop and the {@code do-while} loop.
 */
public class WhileStatement extends Statement {
    private final Expression expression;
    private final List<Statement> body;
    private final boolean do_while;

    /**
     * Constructs a new instance of {@link WhileStatement}.
     *
     * @param expression The condition expression for the loop.
     * @param body The list of statements to execute within the loop body.
     * @param do_while A flag indicating whether the loop is a {@code do-while} loop.
     */
    public WhileStatement(Expression expression, List<Statement> body, boolean do_while) {
        super(NodeType.WhileStatement);
        this.expression = expression;
        this.body = body;
        this.do_while = do_while;
    }

    /**
     * @return The condition {@link Expression} for the loop.
     */
    public Expression expression() {
        return this.expression;
    }

    /**
     * @return The list of statements to execute within the loop body.
     */
    public List<Statement> body() {
        return this.body;
    }

    /**
     * @return {@code true} if the loop is a {@code do-while} loop, {@code false} if it is a traditional {@code while} loop.
     */
    public boolean isDoWhile() {
        return this.do_while;
    }
}

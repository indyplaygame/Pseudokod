package indy.pseudokod.ast;

import java.util.List;

/**
 * Represents an if-statement in the abstract syntax tree (AST).
 */
public class IfStatement extends Statement {
    private final Expression expression;
    private final List<Statement> body;
    private final Statement else_statement;

    /**
     * Constructs a new instance of {@link IfStatement} with an expression, a body, and an else-statement.
     *
     * @param expression The condition expression of the if-statement.
     * @param body The list of statements to be executed if the condition is true.
     * @param else_statement The else/else-if statement to be executed if the condition is false.
     */
    public IfStatement(Expression expression, List<Statement> body, Statement else_statement) {
        super(NodeType.IfStatement);
        this.expression = expression;
        this.body = body;
        this.else_statement = else_statement;
    }

    /**
     * Constructs a new instance of {@link IfStatement} with an expression and a body.
     *
     * @param expression The condition expression of the if-statement.
     * @param body The list of statements to be executed if the condition is true.
     */
    public IfStatement(Expression expression, List<Statement> body) {
        super(NodeType.IfStatement);
        this.expression = expression;
        this.body = body;
        this.else_statement = null;
    }

    /**
     * @return The condition expression of the {@link IfStatement}.
     */
    public Expression expression() {
        return this.expression;
    }

    /**
     * @return The list of {@link Statement} objects in the body of the {@link IfStatement}.
     */
    public List<Statement> body() {
        return this.body;
    }

    /**
     * @return The else statement of the {@link IfStatement}.
     */
    public Statement elseStatement() {
        return this.else_statement;
    }
}

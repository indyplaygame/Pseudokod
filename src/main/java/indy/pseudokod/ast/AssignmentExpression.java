package indy.pseudokod.ast;

/**
 * Represents an assignment expression in the abstract syntax tree (AST).
 * An assignment expression consists of an expression on the left-hand side and a value on the right-hand side.
 * The value is assigned to the expression on the left-hand side.
 */
public class AssignmentExpression extends Expression {
    private final Expression expression;
    private final Expression value;

    /**
     * Constructs a new instance of {@link AssignmentExpression}.
     *
     * @param expression The expression on the left-hand side of the assignment.
     * @param value The value to be assigned to the expression on the left-hand side.
     */
    public AssignmentExpression(Expression expression, Expression value) {
        super(NodeType.AssignmentExpression);
        this.expression = expression;
        this.value = value;
    }

    /**
     * @return The {@link Expression} on the left-hand side of the {@link AssignmentExpression}.
     */
    public Expression expression() {
        return this.expression;
    }

    /**
     * @return The value to be assigned to the expression on the left-hand side.
     */
    public Expression value() {
        return this.value;
    }
}

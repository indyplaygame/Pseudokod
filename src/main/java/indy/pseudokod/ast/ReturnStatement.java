package indy.pseudokod.ast;

/**
 * Represents a return statement in the abstract syntax tree (AST).
 */
public class ReturnStatement extends Statement {
    private final Expression value;

    /**
     * Constructs a new instance of {@link ReturnStatement} with the given expression.
     *
     * @param value The expression to be returned.
     */
    public ReturnStatement(Expression value) {
        super(NodeType.ReturnStatement);
        this.value = value;
    }

    /**
     * @return The {@link Expression} to be returned.
     */
    public Expression value() {
        return this.value;
    }
}

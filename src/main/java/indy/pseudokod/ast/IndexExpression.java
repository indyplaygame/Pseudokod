package indy.pseudokod.ast;

/**
 * Represents an index expression in the abstract syntax tree (AST).
 * This expression consists of an array and an index, allowing access to a specific element in the array.
 */
public class IndexExpression extends Expression {
    private final Expression array;
    private final Expression index;

    /**
     * Constructs a new instance of {@link IndexExpression}.
     *
     * @param array The array being accessed.
     * @param index The index of the element to access.
     */
    public IndexExpression(Expression array, Expression index) {
        super(NodeType.IndexExpression);
        this.array = array;
        this.index = index;
    }

    /**
     * @return The array being accessed.
     */
    public Expression array() {
        return this.array;
    }

    /**
     * @return The index of the element to access.
     */
    public Expression index() {
        return this.index;
    }
}

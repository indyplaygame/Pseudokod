package indy.pseudokod.ast;

/**
 * Represents an ellipsis statement in the abstract syntax tree (AST).
 * An ellipsis statement is used to indicate omitted iterations or elements in a sequence,
 * commonly expressed in mathematical or shorthand notation, such as in:
 * <pre>
 *     for i = 1, 2, ..., n
 * </pre>
 * This statement is used as a placeholder in the AST to represent the continuation of
 * a loop or sequence.
 */
public class EllipsisStatement extends Expression {
    /**
     * Constructs a new instance of {@link EllipsisStatement}.
     */
    public EllipsisStatement() {
        super(NodeType.EllipsisStatement);
    }
}

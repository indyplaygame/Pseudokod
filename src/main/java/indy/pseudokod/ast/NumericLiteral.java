package indy.pseudokod.ast;

/**
 * Represents a number literal in the abstract syntax tree (AST).
 */
public class NumericLiteral extends Expression {
    private final double value;

    /**
     * Constructs a new instance of {@link NumericLiteral} with the given value.
     *
     * @param value The number value represented by this literal.
     */
    public NumericLiteral(String value) {
        super(NodeType.NumericLiteral);
        this.value = Double.parseDouble(value);
    }

    /**
     * @return The number value of this {@link NumericLiteral}.
     */
    public double value() {
        return this.value;
    }
}

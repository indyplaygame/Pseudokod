package indy.pseudokod.ast;

/**
 * Represents a string literal in the abstract syntax tree (AST).
 * A string literal is a constant value enclosed in quotes used to represent text.
 */
public class StringLiteral extends Expression {
    private final String value;

    /**
     * Constructs a new instance of {@link StringLiteral}.
     *
     * @param value The string value represented by this literal.
     */
    public StringLiteral(String value) {
        super(NodeType.StringLiteral);
        this.value = value;
    }

    /**
     * @return The {@link String} value of this {@link StringLiteral}.
     */
    public String value() {
        return this.value;
    }
}

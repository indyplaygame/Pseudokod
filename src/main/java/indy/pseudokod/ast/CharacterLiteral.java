package indy.pseudokod.ast;

/**
 * Represents a character literal in the abstract syntax tree (AST).
 */
public class CharacterLiteral extends Expression {
    private final char value;

    /**
     * Constructs a new instance of {@link CharacterLiteral} with the given value.
     *
     * @param value The character value represented by this literal.
     */
    public CharacterLiteral(char value) {
        super(NodeType.CharacterLiteral);
        this.value = value;
    }

    /**
     * @return The character value of the {@link CharacterLiteral}.
     */
    public char value() {
        return this.value;
    }
}

package indy.pseudokod.ast;

/**
 * Represents an identifier of a variable in the abstract syntax tree (AST).
 */
public class Identifier extends Expression {
    private final String symbol;

    /**
     * Constructs a new instance of {@link Identifier} with the given symbol.
     *
     * @param symbol The identifier of a variable.
     */
    public Identifier(String symbol) {
        super(NodeType.Identifier);
        this.symbol = symbol;
    }

    /**
     * @return The symbol of the identifier.
     */
    public String symbol() {
        return this.symbol;
    }
}

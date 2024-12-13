package indy.pseudokod.ast;

/**
 * Represents a get function in the abstract syntax tree (AST).
 * The get function is used to capture input from the user and assign the value to a specified variable.
 */
public class GetFunction extends Expression {
    private final String identifier;

    /**
     * Constructs a new instance of {@link GetFunction}.
     *
     * @param identifier The identifier of the variable to which the user's input will be assigned.
     */
    public GetFunction(String identifier) {
        super(NodeType.GetFunction);
        this.identifier = identifier;
    }

    /**
     * @return The identifier of the variable to which the user's input will be assigned.
     */
    public String identifier() {
        return this.identifier;
    }
}

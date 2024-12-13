package indy.pseudokod.ast;

import indy.pseudokod.runtime.values.ValueType;
import java.util.List;

/**
 * Represents a function declaration statement in the abstract syntax tree (AST).
 */
public class FunctionDeclaration extends Statement {
    private final String symbol;
    private final List<Statement> data;
    private final ValueType result;
    private final List<Statement> body;

    /**
     * Constructs a new instance of {@link FunctionDeclaration} with given identifier,
     * parameters, variables, return type and the function body .
     *
     * @param symbol The identifier of the function.
     * @param data The list of variables and function parameters.
     * @param result The return type of the function.
     * @param body The list of statements in the function body.
     */

    public FunctionDeclaration(String symbol, List<Statement> data, ValueType result, List<Statement> body) {
        super(NodeType.FunctionDeclaration);
        this.symbol = symbol;
        this.data = data;
        this.result = result;
        this.body = body;
    }

    /**
     * @return The identifier of the function.
     */
    public String symbol() {
        return this.symbol;
    }

    /**
     * @return The list of variables and function parameters.
     */
    public List<Statement> data() {
        return this.data;
    }

    /**
     * @return The return type of the function.
     */
    public ValueType result() {
        return this.result;
    }

    /**
     * @return The list of {@link Statement} objects in the function body.
     */
    public List<Statement> body() {
        return this.body;
    }
}

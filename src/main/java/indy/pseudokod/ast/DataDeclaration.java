package indy.pseudokod.ast;

import java.util.List;

/**
 * Represents a data declaration statement in the abstract syntax tree (AST).
 * A data declaration defines one or more variables with specified data types and optional initial values.
 */
public class DataDeclaration extends Statement {
    private final List<Statement> variables;

    /**
     * Constructs a new instance of {@link DataDeclaration} with the given list of variable declarations.
     *
     * @param statements List of variable declarations.
     */
    public DataDeclaration(List<Statement> statements) {
        super(NodeType.DataDeclaration);
        this.variables = statements;
    }

    /**
     * @return List of variable declarations.
     */
    public List<Statement> body() {
        return this.variables;
    }
}

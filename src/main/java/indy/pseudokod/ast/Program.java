package indy.pseudokod.ast;

import java.util.List;

/**
 * Represents a program in the abstract syntax tree (AST).
 * Contains a list of statements that make up the program's body.
 */
public class Program extends Statement {
    private final List<Statement> body;

    /**
     * Constructs a new instance of {@link Program} with the given list of statements.
     *
     * @param statements The list of statements that make up the program's body.
     */
    public Program(List<Statement> statements) {
        super(NodeType.Program);
        this.body = statements;
    }

    /**
     * @return The list of {@link Statement} objects that make up the program's body.
     */
    public List<Statement> body() {
        return this.body;
    }
}

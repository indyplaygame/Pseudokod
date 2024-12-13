package indy.pseudokod.ast;

import java.util.List;

/**
 * Represents an else statement in the abstract syntax tree (AST).
 */
public class ElseStatement extends Statement {
    private final List<Statement> body;

    /**
     * Constructs a new instance of {@link ElseStatement}.
     *
     * @param body The list of statements that form the body of the else block.
     */
    public ElseStatement(List<Statement> body) {
        super(NodeType.ElseStatement);
        this.body = body;
    }

    /**
     * @return The list of {@link Statement} objects that form the body of the else block.
     */
    public List<Statement> body() {
        return this.body;
    }
}

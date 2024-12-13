package indy.pseudokod.ast;

/**
 * Represents a generic statement in the abstract syntax tree (AST).<br><br>
 *
 * A statement is a basic unit of execution within a program and serves
 * as the base class for more specific statement types.<br><br>
 *
 * A statement performs an action but does not produce a value. It can include
 * control structures (e.g., if-statements, loops), declarations, or procedure calls.
 * Statements form the building blocks of the program's flow and logic but
 * do not inherently return a value.
 */
public class Statement {
    private final NodeType kind;

    /**
     * Constructs a new instance of {@link Statement} with the specified {@link NodeType}.
     *
     * @param kind The specific type of the statement, as defined by {@link NodeType}.
     */
    public Statement(NodeType kind) {
        this.kind = kind;
    }

    /**
     * @return The specific type of this {@link Statement}.
     */
    public NodeType kind() {
        return this.kind;
    }
}

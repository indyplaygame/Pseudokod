package indy.pseudokod.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a for loop statement in the abstract syntax tree (AST).
 */
public class ForStatement extends Statement {
    private final String control_variable;
    private final ArrayList<Expression> values;
    private final List<Statement> body;

    /**
     * Constructs a new instance of {@link ForStatement}.
     *
     * @param control_variable The identifier of the control variable in the for loop.
     * @param values The list of expressions representing the values to iterate over.
     * @param body The list of statements to execute in the for loop body.
     */
    public ForStatement(String control_variable, ArrayList<Expression> values, List<Statement> body) {
        super(NodeType.ForStatement);
        this.control_variable = control_variable;
        this.values = values;
        this.body = body;
    }

    /**
     * @return The identifier of the control variable in the for loop.
     */
    public String control_variable() {
        return this.control_variable;
    }

    /**
     * @return The list of {@link Expression} objects representing the values to iterate over.
     */
    public ArrayList<Expression> values() {
        return this.values;
    }

    /**
     * @return The list of {@link Statement} objects that form the for loop body.
     */
    public List<Statement> body() {
        return this.body;
    }
}

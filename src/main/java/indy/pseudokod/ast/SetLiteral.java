package indy.pseudokod.ast;

import java.util.ArrayList;

/**
 * Represents a set literal in the abstract syntax tree (AST).
 * A set literal is used to define a set of values.
 */
public class SetLiteral extends Expression {
    private final ArrayList<Expression> values;

    /**
     * Constructs a new instance of {@link SetLiteral} with the given list of expressions representing the values in the set.
     *
     * @param values The list of expressions representing the values in the set.
     */
    public SetLiteral(ArrayList<Expression> values) {
        super(NodeType.SetLiteral);
        this.values = values;
    }

    /**
     * @return The list of expressions representing the values in the {@link SetLiteral}.
     */
    public ArrayList<Expression> values() {
        return this.values;
    }
}

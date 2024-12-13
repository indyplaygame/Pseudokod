package indy.pseudokod.ast;

import java.util.ArrayList;

/**
 * Represents an array literal in the abstract syntax tree (AST).
 * An array is a fixed-size, ordered collection of values.
 */
public class ArrayLiteral extends Expression {
    private final ArrayList<Expression> values;

    /**
     * Constructs a new {@link ArrayLiteral} with the given list of expressions.
     *
     * @param values The list of expressions representing the values in the array.
     */
    public ArrayLiteral(ArrayList<Expression> values) {
        super(NodeType.ArrayLiteral);
        this.values = values;
    }

    /**
     * @return The list of {@link Expression} objects representing the values in the array.
     */
    public ArrayList<Expression> values() {
        return this.values;
    }
}

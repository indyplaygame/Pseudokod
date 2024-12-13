package indy.pseudokod.ast;

import java.util.List;

/**
 * Represents a print function in the abstract syntax tree (AST).
 * A print function outputs one or more values to the console or standard output.
 */
public class PrintFunction extends Statement {
    private final List<Expression> args;

    /**
     * Constructs a new instance of {@link PrintFunction} with the given list of arguments.
     *
     * @param args A list of {@link Expression} objects representing the values to be printed.
     */
    public PrintFunction(List<Expression> args) {
        super(NodeType.PrintFunction);
        this.args = args;
    }

    /**
     * @return A list of {@link Expression} objects representing values to be printed.
     */
    public List<Expression> args() {
        return this.args;
    }
}

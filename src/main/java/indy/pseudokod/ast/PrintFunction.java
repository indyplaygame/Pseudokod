package indy.pseudokod.ast;

import java.util.List;

public class PrintFunction extends Statement {

    private final List<Expression> args;

    public PrintFunction(List<Expression> args) {
        super(NodeType.PrintFunction);
        this.args = args;
    }

    public List<Expression> args() {
        return this.args;
    }
}

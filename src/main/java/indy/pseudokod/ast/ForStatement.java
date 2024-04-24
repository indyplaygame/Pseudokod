package indy.pseudokod.ast;

import java.util.ArrayList;
import java.util.List;

public class ForStatement extends Statement {

    private final String control_variable;
    private final ArrayList<Expression> values;
    private final List<Statement> body;

    public ForStatement(String control_variable, ArrayList<Expression> values, List<Statement> body) {
        super(NodeType.ForStatement);
        this.control_variable = control_variable;
        this.values = values;
        this.body = body;
    }

    public String control_variable() {
        return this.control_variable;
    }

    public ArrayList<Expression> values() {
        return this.values;
    }

    public List<Statement> body() {
        return this.body;
    }
}

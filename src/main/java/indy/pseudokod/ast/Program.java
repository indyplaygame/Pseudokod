package indy.pseudokod.ast;

import java.util.ArrayList;
import java.util.List;

public class Program extends Statement {
    NodeType kind;
    List<Statement> statements;

    public Program(List<Statement> statements) {
        this.kind = NodeType.Program;
        this.statements = statements;
    }

    public NodeType kind() {
        return this.kind;
    }

    public List<Statement> body() {
        return this.statements;
    }
}

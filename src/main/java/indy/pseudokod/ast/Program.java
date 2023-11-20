package indy.pseudokod.ast;

import java.util.List;

public class Program extends Statement {
    List<Statement> body;

    public Program(List<Statement> statements) {
        super(NodeType.Program);
        this.body = statements;
    }

    public List<Statement> body() {
        return this.body;
    }
}

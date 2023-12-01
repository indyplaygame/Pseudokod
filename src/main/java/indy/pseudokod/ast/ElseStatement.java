package indy.pseudokod.ast;

import java.util.List;

public class ElseStatement extends Statement {

    List<Statement> body;

    public ElseStatement(List<Statement> body) {
        super(NodeType.ElseStatement);
        this.body = body;
    }

    public List<Statement> body() {
        return this.body;
    }
}

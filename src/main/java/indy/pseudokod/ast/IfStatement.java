package indy.pseudokod.ast;

import java.util.List;

public class IfStatement extends Statement {

    Expression expression;
    List<Statement> body;
    Statement elsestatement;

    public IfStatement(Expression expression, List<Statement> body, Statement elsestatement) {
        super(NodeType.IfStatement);
        this.expression = expression;
        this.body = body;
        this.elsestatement = elsestatement;
    }

    public IfStatement(Expression expression, List<Statement> body) {
        super(NodeType.IfStatement);
        this.expression = expression;
        this.body = body;
    }

    public Expression expression() {
        return this.expression;
    }

    public List<Statement> body() {
        return this.body;
    }

    public Statement else_statement() {
        return this.elsestatement;
    }
}

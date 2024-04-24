package indy.pseudokod.ast;

public class ReturnStatement extends Statement {
    Expression value;
    public ReturnStatement(Expression value) {
        super(NodeType.ReturnStatement);
        this.value = value;
    }

    public Expression value() {
        return this.value;
    }
}

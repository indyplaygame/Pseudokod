package indy.pseudokod.ast;

public class Statement {
    NodeType kind;

    public Statement(NodeType kind) {
        this.kind = kind;
    }

    public NodeType kind() {
        return this.kind;
    }
}

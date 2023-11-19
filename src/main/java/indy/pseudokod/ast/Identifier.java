package indy.pseudokod.ast;

public class Identifier extends Expression {
    NodeType kind;
    String symbol;

    public Identifier(String symbol) {
        this.kind = NodeType.Identifier;
        this.symbol = symbol;
    }

    public NodeType kind() {
        return this.kind;
    }

    public String symbol() {
        return this.symbol;
    }
}

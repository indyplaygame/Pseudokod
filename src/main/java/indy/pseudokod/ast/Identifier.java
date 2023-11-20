package indy.pseudokod.ast;

public class Identifier extends Expression {
    String symbol;

    public Identifier(String symbol) {
        super(NodeType.Identifier);
        this.symbol = symbol;
    }

    public String symbol() {
        return this.symbol;
    }
}

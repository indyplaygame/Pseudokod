package indy.pseudokod.ast;

public class NumericLiteral extends Expression {
    NodeType kind;
    long value;

    public NumericLiteral(String value) {
        this.kind = NodeType.NumericLiteral;
        this.value = Long.parseLong(value);
    }

    public NodeType kind() {
        return this.kind;
    }

    public long value() {
        return this.value;
    }
}

package indy.pseudokod.ast;

public class NumericLiteral extends Expression {
    double value;

    public NumericLiteral(String value) {
        super(NodeType.NumericLiteral);
        this.value = Long.parseLong(value);
    }

    public double value() {
        return this.value;
    }
}

package indy.pseudokod.ast;

public class StringLiteral extends Expression {
    String value;

    public StringLiteral(String value) {
        super(NodeType.StringLiteral);
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}

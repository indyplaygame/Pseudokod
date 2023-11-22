package indy.pseudokod.ast;

public class CharacterLiteral extends Expression {

    private char value;

    public CharacterLiteral(char value) {
        super(NodeType.CharacterLiteral);
        this.value = value;
    }

    public char value() {
        return this.value;
    }
}

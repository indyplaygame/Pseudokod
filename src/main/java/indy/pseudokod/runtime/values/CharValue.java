package indy.pseudokod.runtime.values;

public class CharValue extends RuntimeValue{
    private char value;

    public CharValue(char value) {
        super(ValueType.Char);
        this.value = value;
    }

    public char value() {
        return this.value;
    }
}

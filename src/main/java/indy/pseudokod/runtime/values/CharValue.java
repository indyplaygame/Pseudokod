package indy.pseudokod.runtime.values;

public class CharValue extends RuntimeValue{
    private char value;

    public CharValue(char value) {
        super(ValueType.Char);
        this.value = value;
    }

    public double value() {
        return this.value;
    }
}

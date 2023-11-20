package indy.pseudokod.runtime.values;

public class NumberValue extends RuntimeValue{
    private double value;

    public NumberValue(double value) {
        super(ValueType.Number);
        this.value = value;
    }

    public double value() {
        return this.value;
    }
}

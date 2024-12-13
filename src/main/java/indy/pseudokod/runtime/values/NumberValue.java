package indy.pseudokod.runtime.values;

/**
 * Represents a number value as a {@link RuntimeValue} with type {@link ValueType#Number}.
 */
public class NumberValue extends RuntimeValue {
    private final double value;

    /**
     * Constructs a new instance of {@link NumberValue} with the specified number.
     *
     * @param value The number to be wrapped in this object.
     */
    public NumberValue(double value) {
        super(ValueType.Number);
        this.value = value;
    }

    /**
     * @return The number value encapsulated by this {@link NumberValue}.
     */
    public double value() {
        return this.value;
    }
}

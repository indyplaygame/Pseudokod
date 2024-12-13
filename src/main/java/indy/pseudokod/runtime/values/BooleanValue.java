package indy.pseudokod.runtime.values;

/**
 * Represents a boolean value as a {@link RuntimeValue} with type {@link ValueType#Boolean}.
 */
public class BooleanValue extends RuntimeValue {
    private final boolean value;

    /**
     * Constructs a new instance of {@link BooleanValue} with the specified boolean value.
     *
     * @param value The boolean value to be wrapped in this object.
     */
    public BooleanValue(boolean value) {
        super(ValueType.Boolean);
        this.value = value;
    }

    /**
     * @return The boolean value encapsulated by this {@link BooleanValue}.
     */
    public boolean value() {
        return this.value;
    }
}

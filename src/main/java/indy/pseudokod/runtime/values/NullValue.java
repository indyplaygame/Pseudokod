package indy.pseudokod.runtime.values;

/**
 * Represents a null value, as a {@link RuntimeValue} with type {@link ValueType#NULL}, in the runtime environment.
 */
public class NullValue extends RuntimeValue {
    /**
     * Constructs a new instance of {@link NullValue}.
     */
    public NullValue() {
        super(ValueType.NULL);
    }

    /**
     * @return {@code null}, representing the absence of a value.
     */
    public String value() {
        return null;
    }
}

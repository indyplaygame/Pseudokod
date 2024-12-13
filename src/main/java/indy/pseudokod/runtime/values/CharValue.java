package indy.pseudokod.runtime.values;

/**
 * Represents a character value as a {@link RuntimeValue} with type {@link ValueType#Char}.
 */
public class CharValue extends RuntimeValue {
    private final char value;

    /**
     * Constructs a new instance of {@link CharValue} with the specified character.
     *
     * @param value The character to be wrapped in this object.
     */
    public CharValue(char value) {
        super(ValueType.Char);
        this.value = value;
    }

    /**
     * @return The character value encapsulated by this {@link CharValue}.
     */
    public char value() {
        return this.value;
    }
}

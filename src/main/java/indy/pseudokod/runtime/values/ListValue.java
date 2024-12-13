package indy.pseudokod.runtime.values;

import java.util.List;

/**
 * Represents a list of {@link RuntimeValue} objects as a single {@link RuntimeValue} with type {@link ValueType#List}.
 * This class encapsulates a collection of runtime values and provides utility methods
 * for interacting with the list.
 */
public class ListValue extends RuntimeValue {
    private final List<RuntimeValue> value;

    /**
     * Constructs a new instance of {@link ListValue} with the specified list of {@link RuntimeValue} objects.
     *
     * @param value The list of {@link RuntimeValue} objects to be represented by this {@link ListValue}.
     */
    public ListValue(List<RuntimeValue> value) {
        super(ValueType.List);
        this.value = value;
    }

    /**
     * @return The list of {@link RuntimeValue} objects encapsulated by this {@link ListValue}.
     */
    public List<RuntimeValue> value() {
        return this.value;
    }

    /**
     * @return A {@link NumberValue} representing the size of the list.
     */
    public NumberValue size() {
        return new NumberValue(this.value.size());
    }
}

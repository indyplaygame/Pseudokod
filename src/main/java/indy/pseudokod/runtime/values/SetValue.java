package indy.pseudokod.runtime.values;

import java.util.List;

/**
 * Represents a set of values in the runtime environment.
 * Each set contains unique {@link RuntimeValue} objects and is represented as a {@link RuntimeValue} with the {@link ValueType#Set} type.
 */
public class SetValue extends RuntimeValue{
    private final List<RuntimeValue> value;

    /**
     * Constructs a new instance of {@link SetValue} with the specified list of values.
     *
     * @param value the list of {@link RuntimeValue} objects that compose the set.
     */
    public SetValue(List<RuntimeValue> value) {
        super(ValueType.Set);
        this.value = value;
    }

    /**
     * @return A {@link List} of {@link RuntimeValue} objects in this set.
     */
    public List<RuntimeValue> value() {
        return this.value;
    }

    /**
     * Checks if a specific {@link RuntimeValue} exists within the set.
     * This method uses {@link RuntimeValue#compare(RuntimeValue, RuntimeValue)} to determine equality.
     *
     * @param val the {@link RuntimeValue} to check for.
     * @return {@code true} if the value exists in the set, {@code false} otherwise.
     */
    public boolean includes(RuntimeValue val) {
        return this.value.stream().anyMatch(v -> {
            try {
                return v.type().equals(val.type()) && RuntimeValue.compare(v, val);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
}

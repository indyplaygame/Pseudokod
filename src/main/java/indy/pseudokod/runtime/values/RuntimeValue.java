package indy.pseudokod.runtime.values;

import indy.pseudokod.exceptions.IncompatibleDataTypesException;

/**
 * Represents a base class for all runtime values in the interpreter.
 * Provides a common type system and utilities for working with values of different types.
 */
public class RuntimeValue {
    private final ValueType type;

    /**
     * Constructs a new instance of {@code RuntimeValue} with the specified type.
     *
     * @param type the {@link ValueType} of the runtime value.
     */
    public RuntimeValue(ValueType type) {
        this.type = type;
    }

    /**
     * Retrieves the type of this {@link RuntimeValue}.
     *
     * @return The {@link ValueType} of this value.
     */
    public ValueType type() {
        return this.type;
    }

    /**
     * Compares two {@link RuntimeValue} instances for equality.
     * This method ensures the values are of compatible types or that one of the values is {@code NULL}.
     * If the types are incompatible (other than when one value is {@code NULL}), an exception is thrown.
     *
     * @param value the first {@link RuntimeValue} to compare.
     * @param value1 the second {@link RuntimeValue} to compare.
     * @return {@code true} if the values are equal, {@code false} otherwise.
     * @throws IncompatibleDataTypesException If the types of the two values are incompatible.
     * @throws Throwable If an error occurs while comparing the values.
     */
    public static boolean compare(RuntimeValue value, RuntimeValue value1) throws Throwable {
        if(value.type() != value1.type() && !(value.type().equals(ValueType.NULL) || value1.type().equals(ValueType.NULL)))
            throw new IncompatibleDataTypesException(value.type(), value1.type());

        return StringValue.valueOf(value).value().equals(StringValue.valueOf(value1).value());
    }
}

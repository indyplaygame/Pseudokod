package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

/**
 * Thrown when the provided data type doesn't match the expected data type.
 */
public class DataTypeMismatchException extends Exception {
    /**
     * Constructs a new instance of {@link DataTypeMismatchException}.
     *
     * @param type The expected data type.
     * @param found The provided data type.
     */
    public DataTypeMismatchException(ValueType type, ValueType found) {
        super("Data type '" + type + "' was expected here, but received '" + found + "'.");
    }
}

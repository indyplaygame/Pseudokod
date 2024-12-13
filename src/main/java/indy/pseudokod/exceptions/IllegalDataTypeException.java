package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

/**
 * Thrown when an illegal data type is encountered in a specific context where it is not allowed.
 */
public class IllegalDataTypeException extends Exception {
    /**
     * Constructs a new instance of {@link IllegalDataTypeException}.
     *
     * @param type The name of the data type that is not allowed in the given context.
     */
    public IllegalDataTypeException(String type) {
        super("Data type '" + type + "' is not allowed here.");
    }

    /**
     * Constructs a new instance of {@link IllegalDataTypeException}.
     *
     * @param type The data type, represented by {@link ValueType}, that is not allowed in the given context.
     */
    public IllegalDataTypeException(ValueType type) {
        super("Data type '" + type.name() + "' is not allowed here.");
    }
}

package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

/**
 * Thrown when an attempt is made to convert a value from one data type to another, but the conversion is not allowed.
 */
public class ConversionDataTypeException extends Exception {
    /**
     * Constructs a new instance of {@link ConversionDataTypeException}.
     *
     * @param received The data type, represented by {@link ValueType}, that was attempted to be converted.
     * @param type The target data type, represented by {@link ValueType}, to which the conversion was attempted.
     */
    public ConversionDataTypeException(ValueType received, ValueType type) {
        super(received + " cannot be converted to " + type + ".");
    }
}

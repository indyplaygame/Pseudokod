package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

/**
 * Thrown when operations like comparisons are performed between two values that cannot be logically compared due to type mismatches.
 */
public class IncompatibleDataTypesException extends RuntimeException {
    /**
     * Constructs a new instance of {@link IncompatibleDataTypesException}.
     *
     * @param left The type of the left operand in the comparison.
     * @param right The type of the right operand in the comparison.
     */
    public IncompatibleDataTypesException(ValueType left, ValueType right) {
        super("Incompatible data types, cannot compare '" + left + "' to '" + right + "'.");
    }
}

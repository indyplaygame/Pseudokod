package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.ValueType;

/**
 * Thrown when an attempt is made to call a non-callable data type as if it were callable.
 */
public class InvalidCallableException extends Exception {
    /**
     * Constructs a new instance of {@link InvalidCallableException}.
     *
     * @param type The data type that was attempted to be called.
     */
    public InvalidCallableException(ValueType type) {
        super(type + " is not callable.");
    }
}

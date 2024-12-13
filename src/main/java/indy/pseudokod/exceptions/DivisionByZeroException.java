package indy.pseudokod.exceptions;

/**
 * Thrown when an attempt is made to perform a division by zero operation.
 */
public class DivisionByZeroException extends Exception {
    /**
     * Constructs a new instance of {@link DivisionByZeroException}.
     */
    public DivisionByZeroException() {
        super("Division by zero is not allowed.");
    }
}

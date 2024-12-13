package indy.pseudokod.exceptions;

/**
 * Thrown when there is a {@code true} condition in a while loop.
 */
public class IllegalConditionException extends Exception {
    /**
     * Constructs a new instance of {@link IllegalConditionException}.
     */
    public IllegalConditionException() {
        super("'true' condition in while loop is not allowed.");
    }
}

package indy.pseudokod.exceptions;

/**
 * Thrown when a function or operation is provided with an incorrect number of arguments.
 */
public class ArgumentsAmountException extends Exception {
    /**
     * Constructs a new instance of {@link ArgumentsAmountException}.
     *
     * @param amount The number of arguments provided.
     * @param expected The number of arguments expected.
     */
    public ArgumentsAmountException(int amount, int expected) {
        super("Expected " + expected + " arguments, but received " + amount + ".");
    }
}

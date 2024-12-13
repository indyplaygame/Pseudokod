package indy.pseudokod.exceptions;

/**
 * Thrown when an operation expects a single character but receives an incorrect number of characters.
 */
public class CharactersAmountException extends Exception {
    /**
     * Constructs a new instance of {@link CharactersAmountException}.
     *
     * @param amount The number of characters received.
     */
    public CharactersAmountException(int amount) {
        super("Expected single character (found " + amount + " of them).");
    }
}

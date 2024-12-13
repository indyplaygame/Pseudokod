package indy.pseudokod.exceptions;

/**
 * Thrown when a string literal is not properly terminated with the expected character.
 */
public class StringTerminationException extends Exception {
    /**
     * Constructs a new instance of {@link StringTerminationException}.
     *
     * @param character The character that was expected to terminate the string literal.
     */
    public StringTerminationException(char character) {
        super("Unterminated string literal found, expected the '" + character + "' character to terminate the string.");
    }
}

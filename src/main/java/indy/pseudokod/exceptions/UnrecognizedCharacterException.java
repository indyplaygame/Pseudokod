package indy.pseudokod.exceptions;

/**
 * Thrown when a character is encountered that cannot be recognized and converted into a valid token.
 */
public class UnrecognizedCharacterException extends Exception {
    /**
     * Constructs a new instance of {@link UnrecognizedCharacterException}.
     *
     * @param character The character that is unrecognized and cannot be converted into a token.
     */
    public UnrecognizedCharacterException(String character) {
        super("Character '" + (int) character.charAt(0) + "' is unrecognizable and cannot be converted to Token.");
    }
}

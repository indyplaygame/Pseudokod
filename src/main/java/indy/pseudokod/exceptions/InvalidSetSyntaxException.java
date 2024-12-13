package indy.pseudokod.exceptions;

/**
 * Thrown when an invalid syntax is encountered during the declaration of a set.
 */
public class InvalidSetSyntaxException extends Exception {
    /**
     * Constructs a new instance of {@link InvalidSetSyntaxException}.
     * Provides a message indicating that the syntax of set declaration is invalid and points to the documentation for correction.
     */
    public InvalidSetSyntaxException() {
        super("Invalid set syntax, please check the documentation for valid set syntax.");
    }
}

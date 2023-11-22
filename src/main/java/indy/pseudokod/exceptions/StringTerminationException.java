package indy.pseudokod.exceptions;

public class StringTerminationException extends Exception {
    public StringTerminationException(char character) {
        super("Unterminated string found, expected the '" + character + "' character to terminate the string.");
    }
}

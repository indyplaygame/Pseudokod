package indy.pseudokod.exceptions;

public class UnrecognizedCharacterException extends Exception {
    public UnrecognizedCharacterException(String character) {
        super("Character '" + character + "' is unrecognizable and cannot be converted to Token.");
    }
}

package indy.pseudokod.exceptions;

public class UnrecognizedCharacterException extends Exception {
    public UnrecognizedCharacterException(String character) {
        super("Character '" + (int) character.charAt(0) + "' is unrecognizable and cannot be converted to Token.");
    }
}

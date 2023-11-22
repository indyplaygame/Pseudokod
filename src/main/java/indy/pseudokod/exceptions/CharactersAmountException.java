package indy.pseudokod.exceptions;

public class CharactersAmountException extends Exception {
    public CharactersAmountException(int amount) {
        super("Expected single character (found " + amount + " of them).");
    }
}

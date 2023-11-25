package indy.pseudokod.exceptions;

public class ArgumentsAmountException extends Exception {
    public ArgumentsAmountException(int amount, int expected) {
        super("Expected " + expected + " arguments, but received" + amount + ".");
    }
}

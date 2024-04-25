package indy.pseudokod.exceptions;

public class IllegalConditionException extends Exception {
    public IllegalConditionException() {
        super("'true' condition in while loop is not allowed.");
    }
}

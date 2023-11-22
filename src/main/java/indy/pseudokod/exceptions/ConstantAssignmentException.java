package indy.pseudokod.exceptions;

public class ConstantAssignmentException extends Exception {
    public ConstantAssignmentException(String name) {
        super("Cannot assign value to '" + name + "' because it is a constant.");
    }
}

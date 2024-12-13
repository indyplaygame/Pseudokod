package indy.pseudokod.exceptions;

/**
 * Thrown when attempting to assign a value to a variable that is declared as a constant.
 */
public class ConstantAssignmentException extends Exception {
    /**
     * Constructs a new instance of {@link ConstantAssignmentException}.
     *
     * @param name The name of the constant variable that was attempted to be reassigned.
     */
    public ConstantAssignmentException(String name) {
        super("Cannot assign value to '" + name + "' because it is a constant.");
    }
}

package indy.pseudokod.exceptions;

/**
 * Thrown when attempting to access a variable that has not been declared in the current or any parent scope.
 */
public class VariableNotDeclaredException extends Exception {
    /**
     * Constructs a new instance of {@link VariableNotDeclaredException}.
     *
     * @param name The name of the variable that could not be resolved.
     */
    public VariableNotDeclaredException(String name) {
        super("Cannot resolve a variable name: variable named '" + name + "' was not declared in the scope.");
    }
}

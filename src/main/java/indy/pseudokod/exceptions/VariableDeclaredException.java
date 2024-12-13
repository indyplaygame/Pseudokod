package indy.pseudokod.exceptions;

/**
 * Thrown when attempting to declare a variable with a name that already exists in the current scope.
 */
public class VariableDeclaredException extends Exception {
    /**
     * Constructs a new instance of {@link VariableDeclaredException}.
     *
     * @param name The name of the variable that was attempted to be redeclared.
     */
    public VariableDeclaredException(String name) {
        super("Cannot declare a new variable: variable named '" + name + "' already exists.");
    }
}

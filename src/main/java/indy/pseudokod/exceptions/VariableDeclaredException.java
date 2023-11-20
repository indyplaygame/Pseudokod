package indy.pseudokod.exceptions;

public class VariableDeclaredException extends Exception {
    public VariableDeclaredException(String name) {
        super("Cannot declare a new variable: variable named '" + name + "' is already defined.");
    }
}

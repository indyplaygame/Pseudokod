package indy.pseudokod.exceptions;

public class VariableNotDeclaredException extends Exception {
    public VariableNotDeclaredException(String name) {
        super("Cannot assign a value to variable: variable named '" + name + "' does not exist.");
    }
}

package indy.pseudokod.exceptions;

public class VariableNotDeclaredException extends Exception {
    public VariableNotDeclaredException(String name) {
        super("Cannot resolve a variable name: variable named '" + name + "' does not exist.");
    }
}

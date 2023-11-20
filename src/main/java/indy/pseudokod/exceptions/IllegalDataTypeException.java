package indy.pseudokod.exceptions;

public class IllegalDataTypeException extends Exception {
    public IllegalDataTypeException(String type) {
        super("Data type '" + type + "' is not allowed here.");
    }
}

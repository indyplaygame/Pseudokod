package indy.pseudokod.exceptions;

public class IndexOutOfRangeException extends Exception {
    public IndexOutOfRangeException(int index, int length) {
        super("Index " + index + " out of range for length " + length + ".");
    }
}

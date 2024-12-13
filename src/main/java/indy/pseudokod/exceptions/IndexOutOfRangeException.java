package indy.pseudokod.exceptions;

/**
 * Thrown when an attempt is made to access an element using an index that exceeds the valid range of an array or a list.
 */
public class IndexOutOfRangeException extends Exception {
    /**
     * Constructs a new instance of {@link IndexOutOfRangeException}.
     *
     * @param index The index that was attempted to be accessed.
     * @param length The length of an array or a list.
     */
    public IndexOutOfRangeException(int index, int length) {
        super("Index " + index + " out of range for length " + length + ".");
    }
}

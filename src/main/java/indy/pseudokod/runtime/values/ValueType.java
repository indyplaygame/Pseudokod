package indy.pseudokod.runtime.values;

/**
 * Represents the possible data types of values in the pseudocode interpreter.
 * This enum defines the different types that {@link RuntimeValue} instances can have,
 * which include primitive types like numbers and booleans, as well as more complex types
 * like lists, sets, and functions.
 */
public enum ValueType {
    NULL,
    Number,
    Boolean,
    String,
    Char,
    List,
    Set,
    Range,
    Stack,
    Queue,
    NativeFunction,
    Function
}
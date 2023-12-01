package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.NumberValue;
import indy.pseudokod.runtime.values.RangeValue;

public class NumberOutOfRangeException extends Exception {
    public NumberOutOfRangeException(NumberValue number, RangeValue range) {
        super("Number " + number.value() + " is out of range " + (range.left_included() ? "[" : "(") + range.left_bound() + ", " + range.right_bound() + (range.right_included() ? "]" : ")") + ".");
    }
}

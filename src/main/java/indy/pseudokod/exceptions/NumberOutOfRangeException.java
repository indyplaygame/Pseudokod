package indy.pseudokod.exceptions;

import indy.pseudokod.runtime.values.*;

import java.util.List;

public class NumberOutOfRangeException extends Exception {
    public NumberOutOfRangeException(NumberValue number, RangeValue range) {
        super("Number " + number.value() + " is out of range " + (range.left_inclusive() ? "[" : "(") + range.left_bound() + ", " + range.right_bound() + (range.right_inclusive() ? "]" : ")") + ".");
    }

    public NumberOutOfRangeException(NumberValue number, SetValue set) throws ConversionDataTypeException {
        super("Number " + number.value() + " is not in set " + stringifySet(set) + ".");
    }

    private static String stringifySet(SetValue set) throws ConversionDataTypeException {
        StringBuilder result = new StringBuilder("{");
        List<RuntimeValue> values = set.value();

        if(!values.isEmpty()) result.append(StringValue.valueOf(values.get(0)).value());
        if(values.size() > 1) result.append(", ").append(StringValue.valueOf(values.get(1)).value());
        if(values.size() > 2) result.append(", ..., ").append(StringValue.valueOf(values.get(values.size() - 1)).value());

        result.append("}");
        return result.toString();
    }
}

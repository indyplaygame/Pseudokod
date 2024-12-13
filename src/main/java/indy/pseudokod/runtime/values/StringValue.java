package indy.pseudokod.runtime.values;

import indy.pseudokod.exceptions.ConversionDataTypeException;

/**
 * Represents a string value as a {@link RuntimeValue} with type {@link ValueType#String}.
 */
public class StringValue extends RuntimeValue{
    private final String value;

    /**
     * Constructs a new instance of {@link StringValue} with the specified string.
     *
     * @param value The {@link String} to be wrapped in this object.
     */
    public StringValue(String value) {
        super(ValueType.String);
        this.value = value;
    }

    /**
     * @return The {@link String} value encapsulated by this {@link StringValue}.
     */
    public String value() {
        return this.value;
    }

    /**
     * Converts a given {@link RuntimeValue} to a {@link StringValue}.
     * This method is used for type conversion of various types to strings.
     *
     * @param value the {@link RuntimeValue} to convert.
     * @return tTe resulting {@link StringValue}.
     * @throws ConversionDataTypeException If the {@link RuntimeValue} type cannot be converted to a {@link StringValue}.
     */
    public static StringValue valueOf(RuntimeValue value) throws ConversionDataTypeException {
        return (switch(value.type()) {
            case NULL -> new StringValue("null");
            case Number -> new StringValue(String.valueOf(((NumberValue) value).value()));
            case Char -> new StringValue(String.valueOf(((CharValue) value).value()));
            case Boolean -> new StringValue(String.valueOf(((BooleanValue) value).value()));
            case String -> (StringValue) value;
            default -> throw new ConversionDataTypeException(value.type(), ValueType.String);
        });
    }
}

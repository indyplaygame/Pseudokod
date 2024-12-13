package indy.pseudokod.environment;

import indy.pseudokod.runtime.values.RuntimeValue;
import indy.pseudokod.runtime.values.ValueType;

/**
 * Represents a variable in the runtime environment.
 * A variable consists of a name, type, and value, and can optionally be marked as constant.
 */
public class Variable {
    private final ValueType type;
    private final String name;
    private final boolean constant;
    private RuntimeValue value;

    /**
     * Constructs a new instance of {@link Variable}.
     *
     * @param type The data type of the variable, represented by {@link ValueType}.
     * @param name The name of the variable.
     * @param constant Indicates whether the variable is a constant.
     * @param value The initial value of the variable, represented by {@link RuntimeValue}.
     */
    public Variable(ValueType type, String name, boolean constant, RuntimeValue value) {
        this.type = type;
        this.name = name;
        this.constant = constant;
        this.value = value;
    }

    /**
     * @return The data type of the variable, represented by {@link ValueType}.
     */
    public ValueType type() {
        return this.type;
    }

    /**
     * @return The name of the variable.
     */
    public String name() {
        return this.name;
    }

    /**
     * @return {@code true} if the variable is constant, {@code false} if it is mutable.
     */
    public boolean constant() {
        return this.constant;
    }

    /**
     * @return The current value of the variable, represented by {@link RuntimeValue}.
     */
    public RuntimeValue value() {
        return this.value;
    }

    /**
     * Updates the value of the variable.
     *
     * @param value The new value to be assigned to the variable, represented by {@link RuntimeValue}.
     */
    public void setValue(RuntimeValue value) {
        this.value = value;
    }
}

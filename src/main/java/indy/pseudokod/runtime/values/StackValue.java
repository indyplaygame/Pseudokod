package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ArgumentsAmountException;
import indy.pseudokod.exceptions.IllegalDataTypeException;

import java.util.List;
import java.util.Stack;

/**
 * Represents a stack data structure in the runtime environment.
 * The stack is modeled using a {@link Stack} of {@link RuntimeValue} objects and is represented as a {@link RuntimeValue} with the {@link ValueType#Stack} type.
 */
public class StackValue extends RuntimeValue {
    private final Stack<RuntimeValue> value;

    /**
     * Constructs a new empty instance of {@link StackValue}.
     */
    public StackValue() {
        super(ValueType.Stack);
        this.value = new Stack<>();
    }

    /**
     * Constructs a new instance of {@link StackValue} with the specified stack of values.
     *
     * @param value the stack of {@link RuntimeValue} objects.
     */
    public StackValue(Stack<RuntimeValue> value) {
        super(ValueType.Stack);
        this.value = value;
    }

    /**
     * @return The stack encapsulated by this {@link StackValue}.
     */
    public Stack<RuntimeValue> value() {
        return this.value;
    }

    /**
     * Pushes a {@link RuntimeValue} onto the stack.
     *
     * @param value the value to push onto the stack.
     */
    public void push(RuntimeValue value) {
        this.value.push(value);
    }

    /**
     * Removes and returns the value from the top of the stack.
     *
     * @return The {@link RuntimeValue} at the top of the stack.
     */
    public RuntimeValue pop() {
        return this.value.pop();
    }

    /**
     * Retrieves the value from the top of the stack without removing it.
     *
     * @return The {@link RuntimeValue} at the top of the stack, or {@code null} if the stack is empty.
     */
    public RuntimeValue top() {
        return this.value.peek();
    }

    /**
     * @return The size of the stack as a {@link NumberValue}.
     */
    public NumberValue size() {
        return new NumberValue(this.value.size());
    }

    /**
     * Checks whether the stack is empty.
     *
     * @return A {@link BooleanValue} representing whether the stack is empty.
     */
    public BooleanValue empty() {
        return new BooleanValue(this.value.empty());
    }

    /**
     * A static method to retrieve the top value of a stack passed as an argument, invoked as a {@link NativeFunction}.
     *
     * @param args the list of arguments, expecting a single {@link StackValue}.
     * @param env the {@link Environment} in which the method is executed.
     * @return The top element of the stack as a {@link RuntimeValue}.
     * @throws ArgumentsAmountException If the number of arguments does not equal {@code one}.
     * @throws IllegalDataTypeException If the argument is not of type {@link StackValue}.
     */
    public static RuntimeValue top(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(!(args.get(0) instanceof StackValue stack)) throw new IllegalDataTypeException(args.get(0).type());

        return stack.top();
    }
}

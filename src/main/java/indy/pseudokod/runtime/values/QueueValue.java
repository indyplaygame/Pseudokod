package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ArgumentsAmountException;
import indy.pseudokod.exceptions.IllegalDataTypeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Represents a queue data structure, as a {@link RuntimeValue} with type {@link ValueType#Queue}, in the runtime environment.
 * This class encapsulates a collection of runtime values and provides queue-like operations such as push, pop, and front.
 */
public class QueueValue extends RuntimeValue {
    private final Queue<RuntimeValue> value;

    /**
     * Constructs an empty instance of {@link QueueValue}.
     * The queue is implemented using a {@link LinkedList}.
     */
    public QueueValue() {
        super(ValueType.Queue);
        this.value = new LinkedList<>();
    }

    /**
     * Constructs a new instance of {@link QueueValue} with the specified queue.
     *
     * @param value the initial queue contents as a {@link Queue} of {@link RuntimeValue}.
     */
    public QueueValue(Queue<RuntimeValue> value) {
        super(ValueType.Queue);
        this.value = value;
    }

    /**
     * @return The queue encapsulated by this {@link QueueValue}.
     */
    public Queue<RuntimeValue> value() {
        return this.value;
    }

    /**
     * Adds a new value to the end of the queue.
     *
     * @param value the {@link RuntimeValue} to add.
     */
    public void push(RuntimeValue value) {
        this.value.add(value);
    }

    /**
     * Removes and returns the value from the front of the queue.
     *
     * @return The {@link RuntimeValue} at the front of the queue.
     * @throws java.util.NoSuchElementException if the queue is empty.
     */
    public RuntimeValue pop() {
        return this.value.remove();
    }

    /**
     * Retrieves the value from the front of the queue without removing it.
     *
     * @return The {@link RuntimeValue} at the front of the queue, or {@code null} if the queue is empty.
     */
    public RuntimeValue front() {
        return this.value.peek();
    }

    /**
     * @return The size of the queue as a {@link NumberValue}.
     */
    public NumberValue size() {
        return new NumberValue(this.value.size());
    }

    /**
     * Checks whether the queue is empty.
     *
     * @return A {@link BooleanValue} representing whether the queue is empty.
     */
    public BooleanValue empty() {
        return new BooleanValue(this.value.isEmpty());
    }

    /**
     * Static method to retrieve the front element of a queue, invoked as a {@link NativeFunction}.
     *
     * @param args the list of arguments, expecting a single {@code QueueValue}.
     * @param env the {@link Environment} in which the method is executed.
     * @return The front element of the queue as a {@link RuntimeValue}.
     * @throws ArgumentsAmountException If the number of arguments does not equal {@code one}.
     * @throws IllegalDataTypeException If the argument is not of type {@link QueueValue}.
     */
    public static RuntimeValue front(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(!(args.get(0) instanceof QueueValue queue)) throw new IllegalDataTypeException(args.get(0).type());

        return queue.front();
    }
}

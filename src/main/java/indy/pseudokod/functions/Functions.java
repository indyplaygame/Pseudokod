package indy.pseudokod.functions;

import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ArgumentsAmountException;
import indy.pseudokod.exceptions.IllegalDataTypeException;
import indy.pseudokod.runtime.values.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Provides utility functions for date, time, randomness, waiting, and collection operations (stacks and queues).
 */
public class Functions {
    /**
     * Returns the current date as a string formatted as "dd-MM-yyyy".
     *
     * @param args A list of arguments represented by {@link RuntimeValue} objects (should be empty).
     * @param env The current environment.
     * @return A {@link StringValue} containing the formatted current date.
     * @throws ArgumentsAmountException If any arguments are provided.
     */
    public static RuntimeValue date(List<RuntimeValue> args, Environment env) throws ArgumentsAmountException {
        if(!args.isEmpty()) throw new ArgumentsAmountException(args.size(), 0);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        return new StringValue(format.format(date));
    }

    /**
     * Returns the current time as a string formatted as "HH:mm:ss".
     *
     * @param args A list of arguments representd by {@link RuntimeValue} objects (should be empty).
     * @param env The current environment.
     * @return A {@link StringValue} containing the formatted current time.
     * @throws ArgumentsAmountException If any arguments are provided.
     */
    public static RuntimeValue time(List<RuntimeValue> args, Environment env) throws ArgumentsAmountException {
        if(!args.isEmpty()) throw new ArgumentsAmountException(args.size(), 0);

        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        return new StringValue(format.format(time));
    }

    /**
     * Generates a pseudo-random integer between 0 (inclusive) and {@link Integer#MAX_VALUE} (exclusive).
     *
     * @param args A list of arguments represented by {@link RuntimeValue} objects (should be empty).
     * @param env The current environment.
     * @return A {@link NumberValue} containing the pseudo-random integer.
     * @throws ArgumentsAmountException If any arguments are provided.
     */
    public static RuntimeValue random(List<RuntimeValue> args, Environment env) throws Throwable {
        if(!args.isEmpty()) throw new ArgumentsAmountException(args.size(), 0);
        return new NumberValue(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
    }

    /**
     * Pauses execution for a specified number of milliseconds.
     *
     * @param args A list of arguments represented by {@link RuntimeValue} objects (should contain value of type {@link ValueType#Number}).
     * @param env The current environment.
     * @return A {@link NullValue}.
     * @throws ArgumentsAmountException If the number of arguments does not equal {@code one}.
     * @throws IllegalDataTypeException If the argument is not of type {@link ValueType#Number}.
     */
    public static RuntimeValue wait(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0).type() != ValueType.Number) throw new IllegalDataTypeException(args.get(0).type());

        TimeUnit.MILLISECONDS.sleep((long) ((NumberValue) args.get(0)).value());
        return new NullValue();
    }

    /**
     * Pushes an item onto the top of a stack or to the end of a queue.
     *
     * @param args A list of arguments represented by {@link RuntimeValue} objects (should contain stack or queue represented by a {@link RuntimeValue}
     *             of type {@link ValueType#Stack} or {@link ValueType#Queue} and a {@link RuntimeValue} to be pushed).
     * @param env The current environment.
     * @return A {@link NullValue}.
     * @throws ArgumentsAmountException If the number of arguments does not equal {@code two}.
     * @throws IllegalDataTypeException If the first argument is not of type {@link ValueType#Stack} or {@link ValueType#Queue}.
     */
    public static RuntimeValue push(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 2) throw new ArgumentsAmountException(args.size(), 2);
        if(args.get(0) instanceof StackValue stack) stack.push(args.get(1));
        else if(args.get(0) instanceof QueueValue queue) queue.push(args.get(1));
        else throw new IllegalDataTypeException(args.get(0).type());

        return new NullValue();
    }

    /**
     * Removes and returns the top element from a stack or queue.
     *
     * @param args A list of arguments represented by {@link RuntimeValue} objects (should contain a stack or queue represented by a {@link RuntimeValue}
     *             of type {@link ValueType#Stack} or {@link ValueType#Queue}).
     * @param env The current environment.
     * @return The popped element represented by a {@link RuntimeValue}.
     * @throws ArgumentsAmountException If the number of arguments does not equal {@code one}.
     * @throws IllegalDataTypeException If the argument is not of type {@link ValueType#Stack} or {@link ValueType#Queue}.
     */
    public static RuntimeValue pop(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0) instanceof StackValue stack) return stack.pop();
        else if(args.get(0) instanceof QueueValue queue) return queue.pop();
        else throw new IllegalDataTypeException(args.get(0).type());
    }

    /**
     * Returns the size of a {@link ListValue}, {@link StackValue} or {@link QueueValue}.
     *
     * @param args A list of arguments represented by {@link RuntimeValue} objects (should contain a list, stack or queue represented by a {@link RuntimeValue}
     *             of type {@link ValueType#List}, {@link ValueType#Stack} or {@link ValueType#Queue}).
     * @param env The current environment.
     * @return A {@link NumberValue} containing the size of the collection.
     * @throws ArgumentsAmountException If the number of arguments does not equal {@code one}.
     * @throws IllegalDataTypeException If the argument is not of type {@link ValueType#List}, {@link ValueType#Stack} or {@link ValueType#Queue}.
     */
    public static RuntimeValue size(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0) instanceof ListValue list) return list.size();
        else if(args.get(0) instanceof StackValue stack) return stack.size();
        else if(args.get(0) instanceof QueueValue queue) return queue.size();
        else throw new IllegalDataTypeException(args.get(0).type());
    }

    /**
     * Checks if a {@link StackValue} or {@link QueueValue} is empty.
     *
     * @param args A list of arguments represented by {@link RuntimeValue} objects (should contain a stack or queue represented by a {@link RuntimeValue}
     *             of type {@link ValueType#Stack} or {@link ValueType#Queue}).
     * @param env The current environment.
     * @return A {@link BooleanValue} indicating whether the collection is empty.
     * @throws ArgumentsAmountException If the number of arguments does not equal {@code one}.
     * @throws IllegalDataTypeException If the argument is not of type {@link ValueType#Stack} or {@link ValueType#Queue}.
     */
    public static RuntimeValue empty(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0) instanceof StackValue stack) return stack.empty();
        if(args.get(0) instanceof QueueValue queue) return queue.empty();
        else throw new IllegalDataTypeException(args.get(0).type());
    }
}

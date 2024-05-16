package indy.pseudokod.functions;

import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ArgumentsAmountException;
import indy.pseudokod.exceptions.IllegalDataTypeException;
import indy.pseudokod.exceptions.InvalidConversionDataTypeException;
import indy.pseudokod.runtime.values.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Functions {

    public static RuntimeValue date(List<RuntimeValue> args, Environment env) throws Throwable {
        if(!args.isEmpty()) throw new ArgumentsAmountException(args.size(), 0);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        return new StringValue(format.format(date));
    }

    public static RuntimeValue time(List<RuntimeValue> args, Environment env) throws Throwable {
        if(!args.isEmpty()) throw new ArgumentsAmountException(args.size(), 0);

        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        return new StringValue(format.format(time));
    }

    public static RuntimeValue random(List<RuntimeValue> args, Environment env) throws Throwable {
        if(!args.isEmpty()) throw new ArgumentsAmountException(args.size(), 0);
        return new NumberValue(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
    }

    public static RuntimeValue wait(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0).type() != ValueType.Number) throw new IllegalDataTypeException(args.get(0).type().name());

        TimeUnit.MILLISECONDS.sleep((long) ((NumberValue) args.get(0)).value());

        return new NullValue();
    }

    public static RuntimeValue push(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 2) throw new ArgumentsAmountException(args.size(), 2);
        if(args.get(0) instanceof StackValue stack) stack.push(args.get(1));
        else if(args.get(0) instanceof QueueValue queue) queue.push(args.get(1));
        else throw new IllegalDataTypeException(args.get(0).type().name());

        return new NullValue();
    }

    public static RuntimeValue pop(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0) instanceof StackValue stack) return stack.pop();
        else if(args.get(0) instanceof QueueValue queue) return queue.pop();
        else throw new IllegalDataTypeException(args.get(0).type().name());
    }

    public static RuntimeValue size(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0) instanceof StackValue stack) return stack.size();
        else if(args.get(0) instanceof QueueValue queue) return queue.size();
        else throw new IllegalDataTypeException(args.get(0).type().name());
    }

    public static RuntimeValue empty(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(args.get(0) instanceof StackValue stack) return stack.empty();
        if(args.get(0) instanceof QueueValue queue) return queue.empty();
        else throw new IllegalDataTypeException(args.get(0).type().name());
    }
}

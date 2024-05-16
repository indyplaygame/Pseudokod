package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ArgumentsAmountException;
import indy.pseudokod.exceptions.IllegalDataTypeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueValue extends RuntimeValue {
    private final Queue<RuntimeValue> value;

    public QueueValue() {
        super(ValueType.Queue);
        this.value = new LinkedList<>();
    }

    public QueueValue(Queue<RuntimeValue> value) {
        super(ValueType.Queue);
        this.value = value;
    }

    public Queue<RuntimeValue> value() {
        return this.value;
    }

    public void push(RuntimeValue value) {
        this.value.add(value);
    }

    public RuntimeValue pop() {
        return this.value.remove();
    }

    public RuntimeValue front() {
        return this.value.peek();
    }

    public NumberValue size() {
        return new NumberValue(this.value.size());
    }

    public BooleanValue empty() {
        return new BooleanValue(this.value.isEmpty());
    }

    public static RuntimeValue front(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(!(args.get(0) instanceof QueueValue queue)) throw new IllegalDataTypeException(args.get(0).type().name());

        return queue.front();
    }
}

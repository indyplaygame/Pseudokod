package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ArgumentsAmountException;
import indy.pseudokod.exceptions.IllegalDataTypeException;

import java.util.List;
import java.util.Stack;

public class StackValue extends RuntimeValue {
    private final Stack<RuntimeValue> value;

    public StackValue() {
        super(ValueType.Stack);
        this.value = new Stack<>();
    }

    public StackValue(Stack<RuntimeValue> value) {
        super(ValueType.Stack);
        this.value = value;
    }

    public Stack<RuntimeValue> value() {
        return this.value;
    }

    public void push(RuntimeValue value) {
        this.value.push(value);
    }

    public RuntimeValue pop() {
        return this.value.pop();
    }

    public RuntimeValue top() {
        return this.value.peek();
    }

    public NumberValue size() {
        return new NumberValue(this.value.size());
    }

    public BooleanValue empty() {
        return new BooleanValue(this.value.empty());
    }

    public static RuntimeValue top(List<RuntimeValue> args, Environment env) throws Throwable {
        if(args.size() != 1) throw new ArgumentsAmountException(args.size(), 1);
        if(!(args.get(0) instanceof StackValue stack)) throw new IllegalDataTypeException(args.get(0).type().name());

        return stack.top();
    }
}

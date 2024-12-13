package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;

import java.util.List;

/**
 * Represents a native function in the runtime environment as a {@link RuntimeValue} with type {@link ValueType#NativeFunction}.
 * A native function is a built-in function implemented in Java that can be called
 * within the pseudocode language runtime.
 */
public class NativeFunction extends RuntimeValue {
    private final FunctionCall call;

    /**
     * Constructs a new instance of {@link NativeFunction} with the specified function implementation.
     *
     * @param call The implementation of the native function, represented as a {@link FunctionCall}.
     */
    public NativeFunction(FunctionCall call) {
        super(ValueType.NativeFunction);
        this.call = call;
    }

    /**
     * Invokes the native function with the specified arguments and execution environment.
     *
     * @param args The list of arguments to pass to the native function, represented by {@link RuntimeValue} objects.
     * @param env The {@link Environment} in which the native function is executed.
     * @return The result of the native function call as a {@link RuntimeValue}.
     * @throws Throwable If an error occurs during the function execution.
     */
    public RuntimeValue call(List<RuntimeValue> args, Environment env) throws Throwable {
        return this.call.call(args, env);
    }
}

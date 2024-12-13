package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;

import java.util.List;

/**
 * Represents a call of a function within the pseudocode runtime environment.
 * This interface provides a standard method for invoking functions,
 * ensuring consistent behavior for all callable entities.
 */
public interface FunctionCall {
    /**
     * Executes the function with the provided arguments in the specified environment.
     *
     * @param args A list of {@link RuntimeValue} instances representing the arguments passed to the function.
     * @param env The {@link Environment} in which the function is executed.
     * @return The result of the function execution, represented by a {@link RuntimeValue}.
     * @throws Throwable If any error occurs during the execution of the function.
     */
    RuntimeValue call(List<RuntimeValue> args, Environment env) throws Throwable;
}

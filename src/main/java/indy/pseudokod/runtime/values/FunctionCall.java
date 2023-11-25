package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;

import java.util.List;

public interface FunctionCall {
    RuntimeValue call(List<RuntimeValue> args, Environment env) throws Throwable;
}

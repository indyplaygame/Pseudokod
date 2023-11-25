package indy.pseudokod.runtime.values;

import indy.pseudokod.environment.Environment;

import java.util.List;

public class NativeFunction extends RuntimeValue {

    FunctionCall call;

    public NativeFunction(FunctionCall call) {
        super(ValueType.NativeFunction);
        this.call = call;
    }

    public RuntimeValue call(List<RuntimeValue> args, Environment env) throws Throwable {
        return this.call.call(args, env);
    }
}

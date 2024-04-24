package indy.pseudokod.runtime.values;

import java.util.List;

public class SetValue extends RuntimeValue{
    private final List<RuntimeValue> value;

    public SetValue(List<RuntimeValue> value) {
        super(ValueType.Set);
        this.value = value;
    }

    public List<RuntimeValue> value() {
        return this.value;
    }

    public boolean inSet(RuntimeValue val) {
        return this.value.stream().anyMatch(v -> {
            try {
                return v.type().equals(val.type()) && RuntimeValue.compare(v, val);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
}

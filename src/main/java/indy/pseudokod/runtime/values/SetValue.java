package indy.pseudokod.runtime.values;

import java.util.Set;

public class SetValue extends RuntimeValue{
    private Set<RuntimeValue> value;

    public SetValue(Set<RuntimeValue> value) {
        super(ValueType.String);
        this.value = value;
    }

    public Set<RuntimeValue> value() {
        return this.value;
    }
}

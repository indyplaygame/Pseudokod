package indy.pseudokod.runtime.values;

import java.util.List;

public class ListValue extends RuntimeValue {
    private final List<RuntimeValue> value;

    public ListValue(List<RuntimeValue> value) {
        super(ValueType.List);
        this.value = value;
    }

    public List<RuntimeValue> value() {
        return this.value;
    }
}

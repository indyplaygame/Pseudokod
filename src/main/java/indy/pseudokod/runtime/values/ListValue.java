package indy.pseudokod.runtime.values;

import java.util.List;

public class ListValue extends RuntimeValue{
    private List<RuntimeValue> value;

    public ListValue(List<RuntimeValue> value) {
        super(ValueType.String);
        this.value = value;
    }

    public List<RuntimeValue> value() {
        return this.value;
    }
}
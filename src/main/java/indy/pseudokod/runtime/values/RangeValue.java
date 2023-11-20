package indy.pseudokod.runtime.values;

import java.util.Set;

public class RangeValue extends RuntimeValue{
    private boolean lower_included;
    private double lower_bound;
    private double upper_bound;
    private boolean upper_included;

    public RangeValue(boolean lower_included, double lower_bound, double upper_bound, boolean upper_included) {
        super(ValueType.Range);
        this.lower_included = lower_included;
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
        this.upper_included = upper_included;
    }

    public double lower_bound() {
        return this.lower_bound;
    }

    public double upper_bound() {
        return this.upper_bound;
    }

    public boolean inRange(double number) {
        return ((this.lower_included && this.lower_bound <= number) || !this.lower_included && this.lower_bound < number) &&
               ((this.upper_included && number >= this.upper_bound) || !this.upper_included && number > this.upper_bound);
    }
}

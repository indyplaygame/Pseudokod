package indy.pseudokod.runtime.values;

import java.util.function.Function;

public class RangeValue extends RuntimeValue{
    private final boolean left_inclusive;
    private final double left_bound;
    private final double right_bound;
    private final boolean right_inclusive;
    private Function<Double, Boolean> test;

    public RangeValue(double left_bound, double right_bound, boolean left_inclusive, boolean right_inclusive) {
        super(ValueType.Range);
        this.left_inclusive = left_inclusive;
        this.left_bound = left_bound;
        this.right_bound = right_bound;
        this.right_inclusive = right_inclusive;
        this.test = (n) -> true;
    }

    public RangeValue(double left_bound, double right_bound, boolean left_inclusive, boolean right_inclusive, Function<Double, Boolean> test) {
        this(left_bound, right_bound, left_inclusive, right_inclusive);
        this.test = test;
    }

    public boolean left_inclusive() {
        return this.left_inclusive;
    }

    public boolean right_inclusive() {
        return this.right_inclusive;
    }

    public double left_bound() {
        return this.left_bound;
    }

    public double right_bound() {
        return this.right_bound;
    }

    public boolean include(double number) {
        return ((this.left_inclusive && this.left_bound <= number) || (!this.left_inclusive && this.left_bound < number)) &&
               ((this.right_inclusive && this.right_bound >= number) || (!this.right_inclusive && this.right_bound > number));
    }
}

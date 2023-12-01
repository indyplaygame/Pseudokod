package indy.pseudokod.runtime.values;

public class RangeValue extends RuntimeValue{
    private final boolean left_included;
    private final double left_bound;
    private final double right_bound;
    private final boolean right_included;

    public RangeValue(double left_bound, double right_bound, boolean left_included, boolean right_included) {
        super(ValueType.Range);
        this.left_included = left_included;
        this.left_bound = left_bound;
        this.right_bound = right_bound;
        this.right_included = right_included;
    }

    public boolean left_included() {
        return this.left_included;
    }

    public boolean right_included() {
        return this.right_included;
    }

    public double left_bound() {
        return this.left_bound;
    }

    public double right_bound() {
        return this.right_bound;
    }

    public boolean inRange(double number) {
        return ((this.left_included && this.left_bound <= number) || (!this.left_included && this.left_bound < number)) &&
               ((this.right_included && this.right_bound >= number) || (!this.right_included && this.right_bound > number));
    }
}

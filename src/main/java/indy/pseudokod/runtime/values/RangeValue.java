package indy.pseudokod.runtime.values;

import java.util.function.Function;

/**
 * Represents a range of numeric values, as a {@link RuntimeValue} with type {@link ValueType#Range}, in the runtime environment.
 * This class encapsulates the lower and upper bounds of the range and provides features for checking
 * whether a value is included within the range.
 */
public class RangeValue extends RuntimeValue{
    private final boolean left_inclusive;
    private final double left_bound;
    private final double right_bound;
    private final boolean right_inclusive;
    private final Function<Double, Boolean> test;

    /**
     * Constructs a new instance of {@link RangeValue} with the specified bounds and inclusivity.
     * Assumes all values in the range pass the default test, which always returns {@code true}.
     *
     * @param left_bound the lower bound of the range.
     * @param right_bound the upper bound of the range.
     * @param left_inclusive {@code true} if the lower bound is inclusive, {@code false} otherwise.
     * @param right_inclusive {@code true} if the upper bound is inclusive, {@code false} otherwise.
     */
    public RangeValue(double left_bound, double right_bound, boolean left_inclusive, boolean right_inclusive) {
        this(left_bound, right_bound, left_inclusive, right_inclusive, (n) -> true);
    }

    /**
     * Constructs a new instance of {@link RangeValue} with the specified bounds, inclusivity, and a custom test function.
     * The custom test determines additional conditions for a value to be included in the range.
     *
     * @param left_bound the lower bound of the range.
     * @param right_bound the upper bound of the range.
     * @param left_inclusive {@code true} if the lower bound is inclusive, {@code false} otherwise.
     * @param right_inclusive {@code true} if the upper bound is inclusive, {@code false} otherwise.
     * @param test a {@link Function} that accepts a {@link Double} and returns {@code true}
     *             if the value satisfies additional conditions for inclusion in the range.
     */
    public RangeValue(double left_bound, double right_bound, boolean left_inclusive, boolean right_inclusive, Function<Double, Boolean> test) {
        super(ValueType.Range);
        this.left_inclusive = left_inclusive;
        this.left_bound = left_bound;
        this.right_bound = right_bound;
        this.right_inclusive = right_inclusive;
        this.test = test;
    }

    /**
     * Returns whether the lower bound of the range is inclusive.
     *
     * @return {@code true} if the lower bound is inclusive, {@code false} otherwise.
     */
    public boolean left_inclusive() {
        return this.left_inclusive;
    }

    /**
     * Returns whether the upper bound of the range is inclusive.
     *
     * @return {@code true} if the upper bound is inclusive, {@code false} otherwise.
     */
    public boolean right_inclusive() {
        return this.right_inclusive;
    }

    /**
     * @return The lower bound as a {@code double}.
     */
    public double left_bound() {
        return this.left_bound;
    }

    /**
     * @return The upper bound as a {@code double}.
     */
    public double right_bound() {
        return this.right_bound;
    }

    /**
     * Determines whether a given number is included in the range.
     *
     * @param number the number to check.
     * @return {@code true} if the number is within the range and satisfies the custom test, {@code false} otherwise.
     */
    public boolean include(double number) {
        return ((this.left_inclusive && this.left_bound <= number) || (!this.left_inclusive && this.left_bound < number)) &&
               ((this.right_inclusive && this.right_bound >= number) || (!this.right_inclusive && this.right_bound > number)) && this.test.apply(number);
    }
}

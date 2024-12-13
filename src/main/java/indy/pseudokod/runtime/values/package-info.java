/**
 * The {@link indy.pseudokod.runtime.values}, provides the classes
 * to represent various runtime values within the Pseudokod runtime.
 *
 * <p>During the execution of a Pseudokod program, different types of values such as numbers,
 * strings, booleans, and other data types are dynamically created and manipulated.
 * This package encapsulates these values in a structured and consistent way to enable smooth
 * runtime evaluation and operations.</p>
 *
 * <p>Main Responsibilities:</p>
 * <ul>
 *   <li>Representing different types of runtime values (e.g., numbers, strings, booleans).</li>
 *   <li>Providing utility methods for operations on these values (e.g., arithmetic operations,
 *       comparisons).</li>
 *   <li>Maintaining type safety in the Pseudokod runtime.</li>
 *   <li>Enabling conversions and interactions between different value types where appropriate.</li>
 * </ul>
 *
 * <p>Key Components:</p>
 * <ul>
 *   <li><strong>Value Classes:</strong> Represents specific value types (e.g., {@link indy.pseudokod.runtime.values.ValueType#Number},
 *       {@link indy.pseudokod.runtime.values.ValueType#String}, {@link indy.pseudokod.runtime.values.ValueType#Boolean}) in the Pseudokod runtime environment.</li>
 *   <li><strong>Type-Specific Behavior:</strong> Provides methods for performing type-specific
 *       operations such as checking for existance of a number in a set.</li>
 *   <li><strong>Interoperability:</strong> Supports interactions between different value types,
 *       such as comparing numbers to booleans or converting between strings and numbers.</li>
 * </ul>
 *
 * <p>Example Usage:</p>
 * <pre>{@code
 * // Example: Creating and using runtime values
 * NumberValue number = new NumberValue(42);
 * System.out.println(StringValue.valueOf(number).value()); // Output: 52
 * }</pre>
 *
 * <p>For more details, refer to the individual class documentation in this package.</p>
 *
 * @see indy.pseudokod.runtime.values.RuntimeValue
 * @see indy.pseudokod.runtime.values.ValueType
 */
package indy.pseudokod.runtime.values;
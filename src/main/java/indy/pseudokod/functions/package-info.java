/**
 * The {@link indy.pseudokod.functions} package contains native function implementations
 * for the Pseudokod language. These functions provide essential utilities
 * that are directly accessible during the execution of Pseudokod programs.
 *
 * <p>Native functions in this package are pre-defined and optimized, enabling support for
 * frequently used operations such as handling dates, times, random number generation, execution
 * delays, and managing collections like stacks and queues.</p>
 *
 * <p>Main Responsibilities:</p>
 * <ul>
 *   <li>Providing utility functions for use in Pseudokod programs, such as:</li>
 *   <ul>
 *     <li>Obtaining the current date and time.</li>
 *     <li>Generating pseudo-random numbers.</li>
 *     <li>Pausing program execution for a specified duration.</li>
 *     <li>Operations on dynamic data structures like stacks and queues (e.g., push, pop).</li>
 *   </ul>
 *   <li>Seamlessly integrating with the Pseudokod runtime environment.</li>
 *   <li>Ensuring proper validation of arguments passed to native functions, including type checking
 *       and arity validation.</li>
 * </ul>
 *
 * <p>Key Native Functions:</p>
 * <ul>
 *   <li>{@link indy.pseudokod.functions.Functions#date} - Returns the current date as a formatted string.</li>
 *   <li>{@link indy.pseudokod.functions.Functions#time} - Returns the current time as a formatted string.</li>
 *   <li>{@link indy.pseudokod.functions.Functions#random} - Generates a pseudo-random integer.</li>
 *   <li>{@link indy.pseudokod.functions.Functions#wait} - Pauses execution for a given number of milliseconds.</li>
 *   <li>{@link indy.pseudokod.functions.Functions#push} - Adds an element to a stack or queue.</li>
 *   <li>{@link indy.pseudokod.functions.Functions#pop} - Removes and returns the top element of a stack or queue.</li>
 * </ul>
 *
 * <p>Example Usage:</p>
 * <pre>{@code
 * // Example: Using a native function
 * Environment env = new Environment();
 *
 * // Calling the native 'date' function
 * RuntimeValue date = Functions.date(List.of(), env);
 *
 * // Output: formatted current date
 * System.out.println(StringValue.valueOf(date).value());
 * }</pre>
 *
 * <p>The functions in this package are designed to simplify common programming tasks and enhance
 * the capabilities of Pseudokod programs by leveraging native host features.</p>
 *
 * <p>For more details about specific native functions or extending the functionality, refer to
 * the classes  in this package.</p>
 */
package indy.pseudokod.functions;
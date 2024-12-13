/**
 * The {@link indy.pseudokod.runtime} package provides the foundational components
 * and utilities required for the runtime execution of Pseudokod programs.
 *
 * <p>The runtime is responsible for managing and executing the abstract syntax tree (AST)
 * and ensuring the program behaves as intended during interpretation. It involves the
 * management of runtime values, variables, functions, and program state.</p>
 *
 * <p>Main Responsibilities:</p>
 * <ul>
 *   <li>Executing the parsed Pseudokod programs by interpreting the AST.</li>
 *   <li>Providing support for runtime evaluation of expressions, control flow, and function calls.</li>
 *   <li>Managing program state, including variables and scopes.</li>
 *   <li>Handling runtime errors that occur during program execution, such as division by zero
 *       or accessing undefined variables.</li>
 * </ul>
 *
 * <p>Key Components:</p>
 * <ul>
 *   <li><strong>Runtime Environment:</strong> Manages program state, variable scopes and variables</li>
 *   <li><strong>Expression Evaluator:</strong> Executes expressions and resolves values
 *       dynamically at runtime.</li>
 *   <li><strong>Control Flow Executor:</strong> Interprets and executes control statements
 *       such as loops and conditionals.</li>
 *   <li><strong>Error Handling:</strong> Provides mechanisms to catch and report runtime
 *       errors gracefully to the user.</li>
 * </ul>
 *
 * <p>Example Usage:</p>
 * <pre>{@code
 * // Parse a Pseudokod program
 * Parser parser = new Parser();
 * Program program = parser.produceAST(source);
 *
 * // Example: Evaluating the parsed program
 * Interpreter.evaluate(program);
 * }</pre>
 *
 * <p>For more details, refer to the individual class documentation in this package.</p>
 *
 * @see indy.pseudokod.runtime.Interpreter
 * @see indy.pseudokod.runtime.values.RuntimeValue
 * @see indy.pseudokod.runtime.values.ValueType
 */
package indy.pseudokod.runtime;
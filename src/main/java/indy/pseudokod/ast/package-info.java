/**
 * The {@link indy.pseudokod.ast} package provides classes for representing
 * and manipulating the abstract syntax tree (AST) nodes, such as literals, expressions and statements in the Pseudokod.
 *
 * <p>The Abstract Syntax Tree (AST) is a structured representation of the
 * source code used by the interpreter to analyze and execute the program logic.
 * It captures the hierarchical structure and semantics of the source code,
 * enabling operations such as:
 * </p>
 * <ul>
 *   <li>Parsing and validating the source code.</li>
 *   <li>Interpreting and executing expressions and statements directly.</li>
 *   <li>Performing transformations or enhancements to the code during runtime.</li>
 * </ul>
 *
 * <p>Main Components:</p>
 * <ul>
 *   <li>{@link indy.pseudokod.ast.Statement}: Represents various statements like assignments, loops, and conditional branches.</li>
 *   <li>{@link indy.pseudokod.ast.Expression}: Represents expressions such as arithmetic or logical expressions.</li>
 * </ul>
 *
 * <p>Interpreter Workflow:</p>
 * <ol>
 *   <li>Parse the source code into an AST structure.</li>
 *   <li>Traverse the AST to execute operations in the given order.</li>
 *   <li>Handle runtime logic and state.</li>
 * </ol>
 *
 * <p>Usage Example:</p>
 * <pre>{@code
 * // Example usage of an AST node
 * BinaryExpression expr = new BinaryExpression(leftOperand, rightOperand, "+");
 * }</pre>
 *
 * <p>For details on individual classes, refer to their specific documentation.</p>
 *
 * @see indy.pseudokod.ast.NodeType
 * @see indy.pseudokod.ast.Program
 * @see indy.pseudokod.ast.Statement
 * @see indy.pseudokod.ast.Expression
 * @see indy.pseudokod.parser
 */
package indy.pseudokod.ast;
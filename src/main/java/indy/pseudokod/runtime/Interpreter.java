package indy.pseudokod.runtime;

import indy.pseudokod.ast.*;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.*;
import indy.pseudokod.main.Main;
import indy.pseudokod.parser.Parser;
import indy.pseudokod.runtime.values.*;

import java.util.*;

/**
 * The {@link Interpreter} is responsible for interpreting and evaluating
 * Abstract Syntax Tree (AST) produced by the {@link Parser} and manages program execution within a given environment.
 */
public class Interpreter {

    /**
     * Updates the value of an element in a nested list structure at a given index.
     *
     * @param list The list to update.
     * @param value The new value to set, represented by a {@link RuntimeValue}.
     * @param indexes The list of indexes specifying the nested level to update.
     * @param index The current level index being processed.
     * @throws IndexOutOfRangeException If any index is out of the valid range.
     */
    private static void updateList(List<RuntimeValue> list, RuntimeValue value, List<Integer> indexes, int index) throws Throwable {
        if(indexes.get(index) < 0 || indexes.get(index) > (list.size() - 1)) throw new IndexOutOfRangeException(indexes.get(index) + 1, list.size());
        if(index == 0) list.set(indexes.get(index), value);
        else {
            List<RuntimeValue> sublist = ((ListValue) list.get(indexes.get(index))).value();
            updateList(sublist, value, indexes, index - 1);
            list.set(indexes.get(index), new ListValue(sublist));
        }
    }

    /**
     * Evaluates a complete program by sequentially executing its statements.
     *
     * @param program The program Abstract Syntax Tree, represented by a {@link Program}.
     * @param env The execution environment, represented by an {@link Environment}.
     * @return The result of the final statement evaluation represented by a {@link RuntimeValue}, or a {@code NullValue} if no statements.
     * @throws Throwable If any runtime exception occurs during execution.
     */
    private static RuntimeValue evaluateProgram(Program program, Environment env) throws Throwable {
        RuntimeValue value = new NullValue();

        for(Statement statement : program.body()) {
            value = evaluate(statement, env);
        }

        return value;
    }

    /**
     * Evaluates a binary expression, which can be either numeric binary
     * expression (addition, subtracton, division, multiplication, modulus or integer division) or string concatenation.
     *
     * @param node The {@link BinaryExpression} node representing the expression to evaluate.
     * @param env The execution environment, represented by an {@link Environment}.
     * @return A {@link NumberValue}, if the expression is numeric binary expression, or
     *      a {@link StringValue}, if the expression is string concatenation, representing the result of the bitwise operation.
     * @throws Throwable If the operands have incompatible types or an invalid operator is used.
     */
    private static RuntimeValue evaluateBinaryExpression(BinaryExpression node, Environment env) throws Throwable {
        final RuntimeValue left = evaluate(node.left(), env);
        final RuntimeValue right = evaluate(node.right(), env);

        if(left.type() == ValueType.Number && right.type() == ValueType.Number) return evaluateNumericBinaryExpression((NumberValue) left, (NumberValue) right, node.operator());
        else if((left.type() == ValueType.String || right.type() == ValueType.String) && node.operator().equals("+")) return evaluateConcatenationExpression(left, right);
        else return new NullValue();
    }

    /**
     * Evaluates a comparison expression (equality, inequality, greater-than, less-than, greater-or-equal or less-or-equal).
     *
     * @param node The {@link ComparisonExpression} node representing the expression to evaluate.
     * @param env The execution environment, represented by an {@link Environment}.
     * @return A {@link BooleanValue} representing the result of the comparison.
     * @throws Throwable If the operands have incompatible types or null values are improperly handled.
     */
    private static RuntimeValue evaluateComparisonExpression(ComparisonExpression node, Environment env) throws Throwable {
        final RuntimeValue lhs = evaluate(node.left(), env);
        final RuntimeValue rhs = evaluate(node.right(), env);

        if(lhs.type() != rhs.type() && !(lhs.type() == ValueType.NULL || rhs.type() == ValueType.NULL)) throw new IncompatibleDataTypesException(lhs.type(), rhs.type());

        if(lhs.type() == ValueType.Number && rhs.type() == ValueType.Number) {
            final NumberValue left = (NumberValue) lhs;
            final NumberValue right = (NumberValue) rhs;

            return switch (node.operator()) {
                case "=" -> new BooleanValue(left.value() == right.value());
                case "≠", "!=" -> new BooleanValue(left.value() != right.value());
                case "<" -> new BooleanValue(left.value() < right.value());
                case "≤", "<=" -> new BooleanValue(left.value() <= right.value());
                case ">" -> new BooleanValue(left.value() > right.value());
                case "≥", ">=" -> new BooleanValue(left.value() >= right.value());
                default -> new BooleanValue(false);
            };
        } else {
            final StringValue left = StringValue.valueOf(lhs);
            final StringValue right = StringValue.valueOf(rhs);

            return switch (node.operator()) {
                case "=" -> new BooleanValue(Objects.equals(left.value(), right.value()));
                case "≠", "!=" -> new BooleanValue(!Objects.equals(left.value(), right.value()));
                default -> new BooleanValue(false);
            };
        }
    }

    /**
     * Evaluates a logical expression (AND, OR, NOT, XOR).
     *
     * @param node The {@link LogicalExpression} node representing the expression to evaluate.
     * @param env The execution environment, represented by an {@link Environment}.
     * @return A {@link BooleanValue} representing the result of the logical expression.
     * @throws Throwable If operands are not of boolean type or if an invalid operator is used.
     */
    private static RuntimeValue evaluateLogicalExpression(LogicalExpression node, Environment env) throws Throwable {
        RuntimeValue lhs;
        boolean left = false;
        final RuntimeValue rhs = evaluate(node.right(), env);

        if(node.left() != null) {
            lhs = evaluate(node.left(), env);

            if(lhs.type() != ValueType.Boolean) throw new DataTypeMismatchException(ValueType.Boolean, lhs.type());
            left = ((BooleanValue) lhs).value();
        }

        if(rhs.type() != ValueType.Boolean) throw new DataTypeMismatchException(ValueType.Boolean, rhs.type());

        final boolean right = ((BooleanValue) rhs).value();

        return switch(node.operator()) {
            case "AND", "I", "∧" -> new BooleanValue(left && right);
            case "OR", "LUB", "∨" -> new BooleanValue(left || right);
            case "NOT", "NIE", "¬" -> new BooleanValue(!right);
            case "XOR", "⊕" -> new BooleanValue(left ^ right);
            default -> new BooleanValue(false);
        };
    }

    /**
     * Evaluates a bitwise expression (NOT, AND, OR, XOR, SHIFTS).
     *
     * @param node The {@link BitwiseExpression} node representing the expression to evaluate.
     * @param env The execution environment, represented by an {@link Environment}.
     * @return A {@link NumberValue} representing the result of the bitwise operation.
     * @throws DataTypeMismatchException If either operand is not a number when a number is expected.
     */
    private static RuntimeValue evaluateBitwiseExpression(BitwiseExpression node, Environment env) throws Throwable {
        RuntimeValue lhs;
        int left = 0;
        final RuntimeValue rhs = evaluate(node.right(), env);

        if(node.left() != null) {
            lhs = evaluate(node.left(), env);
            if(!lhs.type().equals(ValueType.Number)) throw new DataTypeMismatchException(ValueType.Boolean, lhs.type());
            left = (int) ((NumberValue) lhs).value();
        }

        if(!rhs.type().equals(ValueType.Number)) throw new DataTypeMismatchException(ValueType.Boolean, rhs.type());
        final int right = (int) ((NumberValue) rhs).value();

        return switch(node.operator()) {
            case "~" -> new NumberValue(~right);
            case "&" -> new NumberValue(left & right);
            case "|" -> new NumberValue(left | right);
            case "^" -> new NumberValue(left ^ right);
            case "<<" -> new NumberValue(left << right);
            case ">>" -> new NumberValue(left >> right);
            default -> new NumberValue(0);
        };
    }

    /**
     * Evaluates an index expression to retrieve a specific element from a list.
     *
     * @param node The {@code IndexExpression} node representing the index expression to evaluate.
     * @param env The execution environment, represented by an {@link Environment}.
     * @return The {@link RuntimeValue} at the specified index in the list.
     * @throws MissingIdentifierException If the array is not identified as an {@link Identifier} or another {@link IndexExpression}.
     * @throws DataTypeMismatchException If the specified array is not of type {@link ValueType#List}.
     * @throws IndexOutOfRangeException If the provided index is out of the valid range for the list.
     */
    private static RuntimeValue evaluateIndexExpression(IndexExpression node, Environment env) throws Throwable {
        if(!(node.array().kind().equals(NodeType.Identifier) || node.array().kind().equals(NodeType.IndexExpression)))
            throw new MissingIdentifierException(NodeType.IndexExpression, node.array());

        final RuntimeValue left = evaluate(node.array(), env);
        if(!left.type().equals(ValueType.List)) throw new DataTypeMismatchException(ValueType.List, left.type());

        final ListValue array = (ListValue) left;
        final RuntimeValue index = evaluate(node.index(), env);

        if(((int) ((NumberValue) index).value()) < 1 || ((int) ((NumberValue) index).value()) > array.value().size())
            throw new IndexOutOfRangeException((int) ((NumberValue) index).value(), array.value().size());

        return array.value().get(((int) ((NumberValue) index).value()) - 1);
    }

    /**
     * Evaluates an assignment expression to update the value of a variable or an element
     * in a nested data structure (e.g. lists with indexes).
     *
     * @param node The {@link AssignmentExpression} node representing the assignment expression to evaluate.
     * @param env The execution environment, represented by an {@link Environment}.
     * @return The {@link RuntimeValue}, which reflects the newly assigned value.
     * @throws MissingIdentifierException If the target of the assignment is not a valid {@link Identifier} or {@link IndexExpression}.
     * @throws DataTypeMismatchException If the value being assigned does not match the expected type.
     * @throws IndexOutOfRangeException If an index in a nested data structure is out of range.
     */
    private static RuntimeValue evaluateAssignment(AssignmentExpression node, Environment env) throws Throwable {
        if(!(node.expression().kind() == NodeType.Identifier || node.expression().kind() == NodeType.IndexExpression))
            throw new MissingIdentifierException(NodeType.AssignmentExpression, node.expression());

        if(node.expression().kind().equals(NodeType.Identifier)) return env.assignVariable(((Identifier) node.expression()).symbol(), evaluate(node.value(), env));
        else {
            Expression expression = node.expression();
            List<Integer> indexes = new ArrayList<>();
            while(expression.kind().equals(NodeType.IndexExpression)) {
                indexes.add((int) ((NumberValue) evaluate(((IndexExpression) expression).index(), env)).value() - 1);
                expression = ((IndexExpression) expression).array();
            }
            String identifier = ((Identifier) expression).symbol();

            List<RuntimeValue> value = ((ListValue) env.getVariable(identifier)).value();
            updateList(value, evaluate(node.value(), env), indexes, indexes.size() - 1);

            return env.assignVariable(identifier, new ListValue(value));
        }
    }


    /**
     * Evaluates a call expression for either a native function or a user-defined function.
     * Processes the arguments, creates a new execution scope for the function, and invokes it within the appropriate environment.
     *
     * @param node The {@link CallExpression} representing the function call to be evaluated.
     * @param env The {@link Environment} within which the call will be evaluated.
     * @return A {@link RuntimeValue} representing the result of the invoked function.
     * @throws RuntimeException If an evaluation of arguments fails.
     * @throws InvalidCallableException If the evaluated expression is not a valid callable (e.g. not a function or native function).
     */
    private static RuntimeValue evaluateCallExpression(CallExpression node, Environment env) throws Throwable {
        final List<RuntimeValue> args = node.args().stream().map(arg -> {
            try {
                return evaluate(arg, env);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }).toList();
        final RuntimeValue function = evaluate(node.expression(), env);

        if(function.type().equals(ValueType.NativeFunction)) {
            return ((NativeFunction) function).call(args, env);
        } else if(function.type().equals(ValueType.Function)) {
            Function fn = (Function) function;
            Environment scope = new Environment(fn.env());
            List<String> parameters = fn.parameters();
            Map<String, ValueType> parameter_types = fn.parameter_types();
            List<String> variables = fn.variables();
            Map<String, ValueType> variable_types = fn.variable_types();

            for(int i = 0; i < parameters.size(); i++) {
                String parameter = parameters.get(i);
                scope.declareVariable(parameter, parameter_types.get(parameter), false, args.get(i));
            }

            for(String variable : variables)
                scope.declareVariable(variable, variable_types.get(variable), false, new NullValue());

            RuntimeValue result = new NullValue();

            for(Statement statement : fn.body())
                result = evaluate(statement, scope);

            return result;
        } else throw new InvalidCallableException(function.type());
    }

    /**
     * Evaluates a return statement and computes the return value.
     * This method processes the expression associated with the return statement
     * and evaluates it within the provided execution environment.
     *
     * @param statement The {@link ReturnStatement} containing the expression to be evaluated.
     * @param env The {@link Environment} in which the return statement is executed.
     * @return The {@link RuntimeValue} representing the result of evaluating the return statement's expression.
     * @throws Throwable If an error occurs during the evaluation of the return statement's expression.
     */
    private static RuntimeValue evaluateReturnStatement(ReturnStatement statement, Environment env) throws Throwable {
        return evaluate(statement.value(), env);
    }

    /**
     * Evaluates an import statement by executing the specified import path.
     * This method processes the import statement and invokes the {@link Main#run(String)} method
     * with the provided path, ensuring that the referenced module or file is appropriately handled.
     *
     * @param statement The {@link ImportStatement} containing the path to be imported.
     * @param env The {@link Environment} in which the import statement is executed (not utilized in this method).
     * @return A {@link NullValue} to indicate that the import statement does not produce a meaningful runtime value.
     * @throws Throwable If an error occurs while processing the import path or during the execution of {@link Main#run(String)}.
     */
    private static RuntimeValue evaluateImportStatement(ImportStatement statement, Environment env) throws Throwable {
        Main.run(statement.path());
        return new NullValue();
    }

    /**
     * Evaluates a numeric binary expression based on the given operator and operands.
     * This method calculates a result by applying the specified binary operator to
     * the provided numeric values and returns the result as a {@link NumberValue}.
     *
     * Supported operators:
     * <ul>
     *     <li>{@code +} - Addition</li>
     *     <li>{@code -} - Subtraction</li>
     *     <li>{@code *} - Multiplication</li>
     *     <li>{@code /} - Division (throws {@link DivisionByZeroException} if the divisor is zero)</li>
     *     <li>{@code div} - Integer division (result rounded down to the nearest whole number)</li>
     *     <li>{@code mod} - Modulus (remainder of division)</li>
     * </ul>
     *
     * @param left The {@link NumberValue} representing the left operand.
     * @param right The {@link NumberValue} representing the right operand.
     * @param operator A {@link String} representing the binary operator ("+", "-", "*", "/", "div", "mod").
     * @return A {@link NumberValue} representing the result of the applied binary operation.
     * @throws DivisionByZeroException If a division by zero is attempted.
     * @throws Throwable For any other unexpected errors during expression evaluation.
     */
    private static NumberValue evaluateNumericBinaryExpression(NumberValue left, NumberValue right, String operator) throws Throwable {
        return new NumberValue(switch (operator) {
            case "+" -> left.value() + right.value();
            case "-" -> left.value() - right.value();
            case "*" -> left.value() * right.value();
            case "/" -> {
                if(right.value() == 0) throw new DivisionByZeroException();
                yield left.value() / right.value();
            }
            case "div" -> {
                if(right.value() == 0) throw new DivisionByZeroException();
                yield Math.floor(left.value() / right.value());
            }
            case "mod" -> left.value() % right.value();
            default -> 0;
        });
    }

    /**
     * Evaluates a concatenation expression by combining the string values of two runtime values.
     * This method converts the provided {@link RuntimeValue} operands into {@link StringValue}
     * and concatenates their string representations in a streamlined approach.
     *
     * @param left The {@link RuntimeValue} representing the left operand.
     * @param right The {@link RuntimeValue} representing the right operand.
     * @return A {@link StringValue} containing the concatenated result of the two operands.
     * @throws Throwable If an error occurs during the conversion of the operands to {@link StringValue}.
     */
    private static StringValue evaluateConcatenationExpression(RuntimeValue left, RuntimeValue right) throws Throwable {
        return new StringValue(StringValue.valueOf(left).value() + StringValue.valueOf(right).value());
    }

    /**
     * Evaluates an identifier by retrieving its corresponding value from the environment.
     * This method resolves the identifier's symbol name and fetches its associated runtime value
     * from the provided {@link Environment}.
     *
     * @param node The {@link Identifier} representing the identifier to be evaluated.
     * @param env The {@link Environment} from which the identifier's value is retrieved.
     * @return The {@link RuntimeValue} associated with the identifier in the environment.
     * @throws Throwable If an error occurs while resolving or retrieving the identifier's value.
     */
    private static RuntimeValue evaluateIdentifier(Identifier node, Environment env) throws Throwable {
        return env.getVariable(node.symbol());
    }

    /**
     * Evaluates a data declaration statement and processes the variables defined within its body.
     * This method handles initializing variables, assigning default values, verifying types,
     * and ensuring that values adhere to specified ranges or constraints.
     *
     * @param node The {@link DataDeclaration} containing the variable declarations and their specifications.
     * @param env The {@link Environment} in which the variables are declared and evaluated.
     * @return A {@link NullValue} to indicate that the data declaration statement does not produce a meaningful runtime value.
     * @throws DataTypeMismatchException If a variable's type does not match its assigned value.
     * @throws NumberOutOfRangeException If a variable's value is outside its defined range or set.
     */
    private static RuntimeValue evaluateDataDeclaration(DataDeclaration node, Environment env) throws Throwable {
        for(Statement statement : node.body()) {
            VariableDeclaration variable = (VariableDeclaration) statement;
            RuntimeValue value = null;
            if(variable.value() != null) value = evaluate(variable.value(), env);

            if(value != null && variable.type() != value.type())
                throw new DataTypeMismatchException(variable.type(), value.type());
            if(variable.type().equals(ValueType.Stack) && variable.value() == null) value = new StackValue();
            if(variable.type().equals(ValueType.Queue) && variable.value() == null) value = new QueueValue();
            if(variable.range() != null) {
                switch(variable.range().kind()) {
                    case RangeLiteral: {
                        RangeValue range = evaluateRange((RangeLiteral) variable.range(), env);

                        assert value != null;
                        if (!range.include(((NumberValue) value).value()))
                            throw new NumberOutOfRangeException((NumberValue) value, range);
                        break;
                    } case SetLiteral:
                        SetValue set = (SetValue) evaluate(variable.range(), env);

                        assert value != null;
                        if(!set.includes(value))
                            throw new NumberOutOfRangeException((NumberValue) value, set);
                        break;
                    case Identifier:
                        RuntimeValue var = env.getVariable(((Identifier) variable.range()).symbol());
                        if(var instanceof RangeValue range) {
                            assert value != null;
                            if (!range.include(((NumberValue) value).value()))
                                throw new NumberOutOfRangeException((NumberValue) value, range);
                        } else throw new DataTypeMismatchException(ValueType.Range, var.type());
                        break;
                }
            }

            env.declareVariable(variable.symbol(), variable.type(), false, Objects.requireNonNullElseGet(value, NullValue::new));
        }

        return new NullValue();
    }

    /**
     * Evaluates a function declaration by registering the function and its components in the environment.
     * This method processes function parameters, local variables, and their respective types, and then
     * declares the new function within the provided {@link Environment}.
     *
     * @param node The {@link FunctionDeclaration} representing the function declaration to be evaluated.
     * @param env The {@link Environment} in which the function is declared.
     * @return A {@link NullValue} to indicate the completion of the evaluation.
     * @throws Throwable If an error occurs during processing, such as unexpected issues with variable or parameter resolution.
     */
    private static RuntimeValue evaluateFunctionDeclaration(FunctionDeclaration node, Environment env) throws Throwable {
        List<String> parameters = new ArrayList<>();
        Map<String, ValueType> parameter_types = new HashMap<>();

        List<String> variables = new ArrayList<>();
        Map<String, ValueType> variable_types = new HashMap<>();

        for(Statement statement : node.data()) {
            VariableDeclaration var_dec = (VariableDeclaration) statement;

            if(!var_dec.symbol().endsWith("*")) {
                variables.add(var_dec.symbol());
                variable_types.put(var_dec.symbol(), var_dec.type());
                continue;
            }

            parameters.add(var_dec.symbol().replace("*", ""));
            parameter_types.put(var_dec.symbol().replace("*", ""), var_dec.type());
        }

        env.declareVariable(node.symbol(), ValueType.Function, true, new Function(parameters, parameter_types, variables, variable_types, node.body(), env));
        return new NullValue();
    }

    /**
     * Evaluates a range literal by calculating its bounds and inclusivity, and returns a {@link RangeValue}.
     * The method computes the left and right bounds of the range and determines whether the bounds are inclusive or exclusive.
     *
     * @param range The {@link RangeLiteral} representing the range to be evaluated.
     * @param env The {@link Environment} used to evaluate the bounds of the range.
     * @return A {@link RangeValue} containing the computed range with its bounds and inclusivity.
     * @throws Throwable If an error occurs during the evaluation of the range bounds.
     */
    private static RangeValue evaluateRange(RangeLiteral range, Environment env) throws Throwable {
        return new RangeValue(((NumberValue) evaluate(range.leftBound(), env)).value(), ((NumberValue) evaluate(range.rightBound(), env)).value(), range.leftIncluded(), range.rightIncluded());
    }

    /**
     * Evaluates an if-statement, including its condition, body, and optional else or else-if statement.
     * This method ensures that the condition is valid and evaluates whether to execute the main body
     * or the associated else/else-if statement based on the condition's result.
     *
     * @param node The {@link IfStatement} representing the if-statement to evaluate.
     * @param env The {@link Environment} in which the condition and statements are evaluated.
     * @return A {@link NullValue} to indicate the completion of the evaluation.
     * @throws InvalidExpressionException If the condition expression is invalid.
     */
    private static RuntimeValue evaluateIfStatement(IfStatement node, Environment env) throws Throwable {
        if(!(node.expression().kind().equals(NodeType.LogicalExpression) || node.expression().kind().equals(NodeType.ComparisonExpression) ||
           node.expression().kind().equals(NodeType.Identifier) || node.expression().kind().equals(NodeType.CallExpression)))
            throw new InvalidExpressionException(NodeType.LogicalExpression, node.expression().kind());

        BooleanValue expression = (BooleanValue) evaluate(node.expression(), env);
        Statement else_statement = node.elseStatement();

        RuntimeValue result = new NullValue();
        if(expression.value()) {
            Environment scope = new Environment(env);
            for(Statement stmt : node.body()) {
                result = evaluate(stmt, scope);
            }
        } else {
            if(else_statement instanceof IfStatement) {
                result = evaluateIfStatement((IfStatement) else_statement, env);
            } else if(else_statement instanceof ElseStatement) {
                result = evaluateElseStatement((ElseStatement) else_statement, env);
            }
        }
        return result;
    }

    /**
     * Evaluates an else-statement by executing the statements within its body.
     * This method creates a new scope for the body of the else-statement and evaluates
     * each statement in the created scope, represented by a {@link Environment}.
     *
     * @param node The {@link ElseStatement} representing the else-statement to evaluate.
     * @param env The {@link Environment} in which the else-statement is evaluated.
     * @throws Throwable If an error occurs while evaluating the statements within the else-statement.
     */
    private static RuntimeValue evaluateElseStatement(ElseStatement node, Environment env) throws Throwable {
        Environment scope = new Environment(env);
        RuntimeValue result = new NullValue();
        for(Statement stmt : node.body()) {
            result = evaluate(stmt, scope);
        }

        return result;
    }

    /**
     * Evaluates a for-statement by iterating over a range of values or a set of given values
     * and executing the body of the loop with each iteration. The control variable is updated
     * at each step based on the type and structure of the loop.
     *
     * @param node The {@link ForStatement} representing the for-statement to evaluate. It contains
     *             the control variable, values for iteration, and the body to execute.
     * @param env  The {@link Environment} in which the for-statement is evaluated. A new scope is
     *             created for the loop's execution to encapsulate its variables.
     * @return A {@link NullValue} to indicate the completion of the loop evaluation.
     * @throws InvalidSetSyntaxException If the syntax for range-based iteration (using an ellipsis `...`) is invalid.
     * @throws RuntimeException If there is a failure during runtime evaluation of expressions, values, or statements.
     * @throws Throwable If any other unexpected errors occur during evaluation, such as null pointer exceptions,
     *                  invalid states, or unchecked exceptions from the internal implementation.
     */
    private static RuntimeValue evaluateForStatement(ForStatement node, Environment env) throws Throwable {
        Environment scope = new Environment(env);

        if(node.values().stream().anyMatch(e -> e.kind().equals(NodeType.EllipsisStatement))) {
            // TODO: Extract the ellipsis statement evaluation
            if(node.values().size() < 4) throw new InvalidSetSyntaxException();

            RuntimeValue first = evaluate(node.values().get(0), env);
            RuntimeValue second = evaluate(node.values().get(1), env);
            RuntimeValue max = evaluate(node.values().get(3), env);

            if(!(first.type().equals(ValueType.Number) && second.type().equals(ValueType.Number) &&
                    node.values().get(2).kind().equals(NodeType.EllipsisStatement) && max.type().equals(ValueType.Number))) throw new InvalidSetSyntaxException();

            int step = (int) (((NumberValue) second).value() - ((NumberValue) first).value());

            scope.declareVariable(node.control_variable(), ValueType.Number, false, first);

            while((int) ((NumberValue) scope.getVariable(node.control_variable())).value() <= (int) ((NumberValue) max).value()) {
                for(Statement statement : node.body()) {
                    evaluate(statement, scope);
                }
                scope.assignVariable(node.control_variable(), new NumberValue((int) ((NumberValue) scope.getVariable(node.control_variable())).value() + step));
            }
        } else {
            List<RuntimeValue> values = node.values().stream().map(v -> {
                try {
                    return evaluate(v, scope);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }).toList();

            scope.declareVariable(node.control_variable(), ValueType.Number, false, new NullValue());

            for(RuntimeValue value : values) {
                scope.assignVariable(node.control_variable(), value);
                for(Statement statement : node.body()) {
                    evaluate(statement, scope);
                }
            }
        }

        return new NullValue();
    }

    /**
     * Evaluates a while or do-while loop by repeatedly executing its body until the loop's condition evaluates to false.
     * The condition is evaluated before each iteration for a while-loop or after each iteration for a do-while loop.
     *
     * @param node The {@link WhileStatement} representing the while-loop to evaluate. It contains the loop condition
     *             and the statements that make up the body of the loop.
     * @param env The {@link Environment} in which the while loop statement is evaluated. A new local scope is created for
     *             the loop to maintain variable isolation.
     * @return A {@link NullValue}, to indicate the completion of the loop evaluation.
     * @throws IllegalConditionException If the condition of the while-loop evaluates to a literal "true",
     *          which represents an invalid infinite loop scenario.
     * @throws InvalidWhileLoopExpressionException If the loop's condition evaluates to a value that is not boolean-compatible,
     *          such as when the expression cannot be cast to a {@link BooleanValue}.
     * @throws Throwable If any unexpected runtime error occurs during the loop's evaluation or execution.
     *          This serves as a safeguard for unhandled errors from internal implementation.
     */
    private static RuntimeValue evaluateWhileStatement(WhileStatement node, Environment env) throws Throwable {
        Environment scope = new Environment(env);

        if(node.expression().kind() == NodeType.Identifier && (((Identifier) node.expression()).symbol().equals("true") || ((Identifier) node.expression()).symbol().equals("prawda")))
            throw new IllegalConditionException();

        RuntimeValue expr = evaluate(node.expression(), env);

        try {
            if(node.isDoWhile()) {
                BooleanValue expr_val;

                do {
                    for(Statement statement : node.body()) {
                        evaluate(statement, scope);
                    }
                    expr_val = (BooleanValue) evaluate(node.expression(), env);
                } while(expr_val.value());
            } else {
                BooleanValue expr_val = (BooleanValue) expr;

                while(expr_val.value()) {
                    for(Statement statement : node.body()) {
                        evaluate(statement, scope);
                    }
                    expr_val = (BooleanValue) evaluate(node.expression(), env);
                }
            }
        } catch(ClassCastException e) {
            throw new InvalidWhileLoopExpressionException(expr.type());
        }

        return new NullValue();
    }

    /**
     * Evaluates a given {@link Statement} node in the context of the provided environment.
     * This method handles all types of statements and expressions, including literals, operations, control structures,
     * and function executions. The evaluation results in a {@link RuntimeValue} corresponding to the type of the statement.
     *
     * @param node The {@link Statement} instance to evaluate. This is the abstract syntax tree (AST) node
     *             representing a part of the processed program.
     * @param env The {@link Environment} in which the statement is evaluated. It stores variables, functions,
     *            and types and provides context for evaluation.
     * @return A {@link RuntimeValue} representing the result of evaluating the given statement.
     *         This value could be of types such as {@link NullValue}, {@link NumberValue}, {@link StringValue}, or others
     *         depending on the type of the evaluated statement.
     * @throws InvalidSetSyntaxException If an invalid range syntax is used in a set literal (e.g. invalid types,
     *          or incorrect ellipsis placement).
     * @throws ConversionDataTypeException If a value cannot be properly converted to the expected type
     *          during statement evaluation.
     * @throws ASTNodeNotSetupException If the statement's kind is not properly set up or it is not recognized
     *          by the evaluation logic. This applies to unsupported or unimplemented nodes.
     * @throws RuntimeException If any runtime error occurs during the evaluation of the given statement.
     * @throws Throwable If any unexpected unchecked exception or error occurs during evaluation.
     */
    public static RuntimeValue evaluate(Statement node, Environment env) throws Throwable {
        switch(node.kind()) {
            case NumericLiteral:
                return new NumberValue(((NumericLiteral) node).value());
            case StringLiteral:
                return new StringValue(((StringLiteral) node).value());
            case CharacterLiteral:
                return new CharValue(((CharacterLiteral) node).value());
            case ArrayLiteral: {
                ArrayList<RuntimeValue> values = new ArrayList<>();
                for (Expression expression : ((ArrayLiteral) node).values())
                    values.add(evaluate(expression, env));

                return new ListValue(values);
            } case SetLiteral:
                List<RuntimeValue> values = new ArrayList<>();
                SetLiteral set = (SetLiteral) node;

                if(set.values().stream().anyMatch(e -> e.kind().equals(NodeType.EllipsisStatement))) {
                    // TODO: Extract the ellipsis statement evaluation
                    if(set.values().size() < 4) throw new InvalidSetSyntaxException();

                    RuntimeValue first = evaluate(set.values().get(0), env);
                    RuntimeValue second = evaluate(set.values().get(1), env);
                    RuntimeValue max = evaluate(set.values().get(3), env);

                    if(!(first.type().equals(ValueType.Number) && second.type().equals(ValueType.Number) &&
                       set.values().get(2).kind().equals(NodeType.EllipsisStatement) && max.type().equals(ValueType.Number))) throw new InvalidSetSyntaxException();

                    int step = (int) (((NumberValue) second).value() - ((NumberValue) first).value());

                    for(int i = ((int) ((NumberValue) first).value()); i <= (int) ((NumberValue) max).value(); i += step) {
                        values.add(new NumberValue(i));
                    }
                    return new SetValue(values);
                }

                for(Expression expression : set.values()) {
                    if(expression.kind().equals(NodeType.EllipsisStatement)) throw new InvalidSetSyntaxException();

                    RuntimeValue value = evaluate(expression, env);

                    if(values.stream().noneMatch(e -> {
                        try {
                            return e.type() == value.type() && StringValue.valueOf(e).value().equals(StringValue.valueOf(value).value());
                        } catch (ConversionDataTypeException ex) {
                            ex.printStackTrace();
                            return false;
                        }
                    })) values.add(value);
                }
                return new SetValue(values);
            case Identifier:
                return evaluateIdentifier((Identifier) node, env);
            case BinaryExpression:
                return evaluateBinaryExpression((BinaryExpression) node, env);
            case ComparisonExpression:
                return evaluateComparisonExpression((ComparisonExpression) node, env);
            case LogicalExpression:
                return evaluateLogicalExpression((LogicalExpression) node, env);
            case BitwiseExpression:
                return evaluateBitwiseExpression((BitwiseExpression) node, env);
            case IndexExpression:
                return evaluateIndexExpression((IndexExpression) node, env);
            case Program:
                return evaluateProgram((Program) node, env);
            case DataDeclaration:
                return evaluateDataDeclaration((DataDeclaration) node, env);
            case FunctionDeclaration:
                return evaluateFunctionDeclaration((FunctionDeclaration) node, env);
            case AssignmentExpression:
                return evaluateAssignment((AssignmentExpression) node, env);
            case CallExpression:
                return evaluateCallExpression((CallExpression) node, env);
            case PrintFunction:
                StringBuilder output = new StringBuilder();
                ((PrintFunction) node).args().forEach(arg -> {
                    try {
                        RuntimeValue value = evaluate(arg, env);

                        output.append(StringValue.valueOf(value).value()
                                .replace("\\n", "\n")
                                .replace("\\\"", "\""));
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                });

                System.out.print(output);
                return new NullValue();
            case GetFunction:
                Scanner scanner = new Scanner(System.in);

                String name = ((GetFunction) node).identifier();
                String value = scanner.nextLine();
                ValueType type = env.getVariableType(name);

                if(value == null || value.isBlank()) return env.assignVariable(name, new NullValue());

                if(type.equals(ValueType.Number)) return env.assignVariable(name, new NumberValue(Double.parseDouble(value)));
                else if(type.equals(ValueType.Boolean)) return env.assignVariable(name, new BooleanValue(Boolean.getBoolean(value)));
                else if(type.equals(ValueType.Char)) return env.assignVariable(name, new CharValue(value.charAt(0)));
                else if(type.equals(ValueType.String)) return env.assignVariable(name, new StringValue(value));
                else throw new ConversionDataTypeException(ValueType.String, type);
            case IfStatement:
                return evaluateIfStatement((IfStatement) node, env);
            case ElseStatement:
                return new NullValue();
            case ForStatement:
                return evaluateForStatement((ForStatement) node, env);
            case WhileStatement:
                return evaluateWhileStatement((WhileStatement) node, env);
            case ReturnStatement:
                return evaluateReturnStatement((ReturnStatement) node, env);
            case ImportStatement:
                return evaluateImportStatement((ImportStatement) node, env);
            default:
                throw new ASTNodeNotSetupException(node.kind());
        }
    }
}
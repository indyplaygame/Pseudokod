package indy.pseudokod.runtime;

import indy.pseudokod.ast.*;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.*;
import indy.pseudokod.runtime.values.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Interpreter {

    private static void updateList(List<RuntimeValue> list, RuntimeValue value, List<Integer> indexes, int index) throws Throwable {
        if(indexes.get(index) < 0 || indexes.get(index) > (list.size() - 1)) throw new IndexOutOfRangeException(indexes.get(index) + 1, list.size());
        if(index == 0) list.set(indexes.get(index), value);
        else {
            List<RuntimeValue> sublist = ((ListValue) list.get(indexes.get(index))).value();
            updateList(sublist, value, indexes, index - 1);
            list.set(indexes.get(index), new ListValue(sublist));
        }
    }

    static RuntimeValue evaluateProgram(Program program, Environment env) throws Throwable {
        RuntimeValue value = new NullValue();

        for(Statement statement : program.body()) {
            value = evaluate(statement, env);
        }

        return value;
    }

    static RuntimeValue evaluateBinaryExpression(BinaryExpression node, Environment env) throws Throwable {
        final RuntimeValue left = evaluate(node.left(), env);
        final RuntimeValue right = evaluate(node.right(), env);

        if(left.type() == ValueType.Number && right.type() == ValueType.Number) return evaluateNumericBinaryExpression((NumberValue) left, (NumberValue) right, node.operator());
        else if((left.type() == ValueType.String || right.type() == ValueType.String) && node.operator().equals("+")) return evaluateConcatenationExpression(left, right);
        else return new NullValue();
    }

    static RuntimeValue evaluateComparisonExpression(ComparisonExpression node, Environment env) throws Throwable {
        final RuntimeValue lhs = evaluate(node.left(), env);
        final RuntimeValue rhs = evaluate(node.right(), env);

        if(lhs.type() != rhs.type() && !(lhs.type() == ValueType.NULL || rhs.type() == ValueType.NULL)) throw new IncompatibleDataTypeException(lhs.type(), rhs.type(), 0);

        if(lhs.type() == ValueType.Number && rhs.type() == ValueType.Number) {
            final NumberValue left = (NumberValue) lhs;
            final NumberValue right = (NumberValue) rhs;

            switch(node.operator()) {
                case "=":
                    return new BooleanValue(left.value() == right.value());
                case "≠":
                case "!=":
                    return new BooleanValue(left.value() != right.value());
                case "<":
                    return new BooleanValue(left.value() < right.value());
                case "≤":
                case "<=":
                    return new BooleanValue(left.value() <= right.value());
                case ">":
                    return new BooleanValue(left.value() > right.value());
                case "≥":
                case ">=":
                    return new BooleanValue(left.value() >= right.value());
                default:
                    return new BooleanValue(false);
            }
        } else {
            final StringValue left = StringValue.valueOf(lhs);
            final StringValue right = StringValue.valueOf(rhs);

            switch(node.operator()) {
                case "=":
                    return new BooleanValue(Objects.equals(left.value(), right.value()));
                case "≠":
                case "!=":
                    return new BooleanValue(!Objects.equals(left.value(), right.value()));
                default:
                    return new BooleanValue(false);
            }
        }
    }

    static RuntimeValue evaluateLogicalExpression(LogicalExpression node, Environment env) throws Throwable {
        RuntimeValue lhs;
        BooleanValue left = new BooleanValue(false);
        final RuntimeValue rhs = evaluate(node.right(), env);

        if(node.left() != null) {
            lhs = evaluate(node.left(), env);

            if(lhs.type() != ValueType.Boolean) throw new IncompatibleDataTypeException(ValueType.Boolean, lhs.type());
            left = (BooleanValue) lhs;
        }

        if(rhs.type() != ValueType.Boolean) throw new IncompatibleDataTypeException(ValueType.Boolean, rhs.type());

        final BooleanValue right = (BooleanValue) rhs;

        switch(node.operator()) {
            case "AND":
            case "I":
            case "∧":
                return new BooleanValue(left.value() && right.value());
            case "OR":
            case "LUB":
            case "∨":
                return new BooleanValue(left.value() || right.value());
            case "NOT":
            case "NIE:":
            case "~":
            case "¬":
                return new BooleanValue(!right.value());
            default:
                return new BooleanValue(false);
        }
    }

    static RuntimeValue evaluateIndexExpression(IndexExpression node, Environment env) throws Throwable {
        if(!(node.array().kind().equals(NodeType.Identifier) || node.array().kind().equals(NodeType.IndexExpression)))
            throw new MissingIdentifierException(NodeType.IndexExpression, node.array());

        final RuntimeValue left = evaluate(node.array(), env);

        if(!left.type().equals(ValueType.List)) throw new IncompatibleDataTypeException(ValueType.List, left.type());

        final ListValue array = (ListValue) left;
        final RuntimeValue index = evaluate(node.index(), env);

        if(((int) ((NumberValue) index).value()) < 1 || ((int) ((NumberValue) index).value()) > array.value().size())
            throw new IndexOutOfRangeException((int) ((NumberValue) index).value(), array.value().size());

        return array.value().get(((int) ((NumberValue) index).value()) - 1);
    }

    static RuntimeValue evaluateAssignment(AssignmentExpression node, Environment env) throws Throwable {
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

    static RuntimeValue evaluateCallExpression(CallExpression node, Environment env) throws Throwable {
        final List<RuntimeValue> args = node.args().stream().map(arg -> {
            try {
                return evaluate(arg, env);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }).toList();
        final RuntimeValue function = evaluate(node.expression(), env);

        if(function.type().equals(ValueType.NativeFunction)) return ((NativeFunction) function).call(args, env);
        else throw new InvalidCallableException(function.type());
    }

    static NumberValue evaluateNumericBinaryExpression(NumberValue left, NumberValue right, String operator) throws Throwable {
        double result = 0;

        switch(operator) {
            case "+":
                result = left.value() + right.value();
                break;
            case "-":
                result = left.value() - right.value();
                break;
            case "*":
                result = left.value() * right.value();
                break;
            case "/":
                if (right.value() == 0) throw new DivisionByZeroException();
                result = left.value() / right.value();
                break;
            case "div":
                result = Math.floor(left.value() / right.value());
                break;
            case "mod":
                result = left.value() % right.value();
                break;
        }

        return new NumberValue(result);
    }

    static StringValue evaluateConcatenationExpression(RuntimeValue left, RuntimeValue right) throws Throwable {
        StringValue lhs = StringValue.valueOf(left);
        StringValue rhs = StringValue.valueOf(right);

        return new StringValue(lhs.value() + rhs.value());
    }

    static RuntimeValue evaluateIdentifier(Identifier node, Environment env) throws Throwable {
        return env.getVariable(node.symbol());
    }

    static RuntimeValue evaluateDataDeclaration(DataDeclaration node, Environment env) throws Throwable {
        for(Statement statement : node.body()) {
            VariableDeclaration variable = (VariableDeclaration) statement;
            RangeValue range = null;
            RuntimeValue value = evaluate(variable.value(), env);

            if(variable.type() != value.type()) throw new IncompatibleDataTypeException(variable.type(), value.type());
            if(variable.range() != null) range = evaluateRange(variable.range(), env);

            if(range != null && !range.inRange(((NumberValue) value).value()))
                throw new NumberOutOfRangeException((NumberValue) value, range);

            if(variable.value() != null) env.declareVariable(variable.symbol(), variable.type(), false, value);
            else env.declareVariable(variable.symbol(), variable.type(), false, new NullValue());
        }

        return new NullValue();
    }

    static RangeValue evaluateRange(RangeLiteral range, Environment env) throws Throwable {
        return new RangeValue(((NumberValue) evaluate(range.left_bound(), env)).value(), ((NumberValue) evaluate(range.right_bound(), env)).value(), range.left_included(), range.right_included());
    }

    static RuntimeValue evaluateIfStatement(IfStatement node, Environment env) throws Throwable {
        if(!(node.expression().kind().equals(NodeType.LogicalExpression) || node.expression().kind().equals(NodeType.ComparisonExpression) || node.expression().kind().equals(NodeType.Identifier)))
            throw new MissingExpressionException(NodeType.LogicalExpression, node.expression().kind());

        BooleanValue expression = (BooleanValue) evaluate(node.expression(), env);
        Statement else_statement = node.else_statement();

        if(expression.value()) {
            Environment scope = new Environment(env);
            for(Statement stmt : node.body()) {
                evaluate(stmt, scope);
            }
        } else {
            if(else_statement instanceof IfStatement) {
                evaluateIfStatement((IfStatement) else_statement, env);
            } else if(else_statement instanceof ElseStatement) {
                evaluateElseStatement((ElseStatement) else_statement, env);
            }
        }
        return new NullValue();
    }

    static void evaluateElseStatement(ElseStatement node, Environment env) throws Throwable {
        Environment scope = new Environment(env);
        for(Statement stmt : node.body()) {
            evaluate(stmt, env);
        }

        new NullValue();
    }

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
                for(Expression expression : ((ArrayLiteral) node).values()) {
                    values.add(evaluate(expression, env));
                }
                return new ListValue(values); }
            case SetLiteral:
                List<RuntimeValue> values = new ArrayList<>();
                SetLiteral set = (SetLiteral) node;

                if(set.values().stream().anyMatch(e -> e.kind().equals(NodeType.ContinueStatement))) {
                    if(set.values().size() < 4) throw new InvalidSetSyntaxException();

                    RuntimeValue first = evaluate(set.values().get(0), env);
                    RuntimeValue second = evaluate(set.values().get(1), env);
                    RuntimeValue max = evaluate(set.values().get(3), env);

                    if(!(first.type().equals(ValueType.Number) && second.type().equals(ValueType.Number) &&
                       set.values().get(2).kind().equals(NodeType.ContinueStatement) && max.type().equals(ValueType.Number))) throw new InvalidSetSyntaxException();

                    int step = (int) (((NumberValue) second).value() - ((NumberValue) first).value());

                    for(int i = ((int) ((NumberValue) first).value()); i <= (int) ((NumberValue) max).value(); i += step) {
                        values.add(new NumberValue(i));
                    }
                    return new SetValue(values);
                }

                for(Expression expression : set.values()) {
                    if(expression.kind().equals(NodeType.ContinueStatement)) throw new InvalidSetSyntaxException();

                    RuntimeValue value = evaluate(expression, env);

                    if(values.stream().noneMatch(e -> {
                        try {
                            return e.type() == value.type() && StringValue.valueOf(e).value().equals(StringValue.valueOf(value).value());
                        } catch (InvalidConversionDataTypeException ex) {
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
            case IndexExpression:
                return evaluateIndexExpression((IndexExpression) node, env);
            case Program:
                return evaluateProgram((Program) node, env);
            case DataDeclaration:
                return evaluateDataDeclaration((DataDeclaration) node, env);
            case AssignmentExpression:
                return evaluateAssignment((AssignmentExpression) node, env);
            case CallExpression:
                return evaluateCallExpression((CallExpression) node, env);
            case PrintFunction:
                StringBuilder output = new StringBuilder();
                ((PrintFunction) node).args().forEach(arg -> {
                    try {
                        RuntimeValue value = evaluate(arg, env);

                        output.append(StringValue.valueOf(value).value());
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

                if(value == null || value.isBlank() || value.isEmpty()) return env.assignVariable(name, new NullValue());

                if(type.equals(ValueType.Number)) return env.assignVariable(name, new NumberValue(Double.parseDouble(value)));
                else if(type.equals(ValueType.Boolean)) return env.assignVariable(name, new BooleanValue(Boolean.getBoolean(value)));
                else if(type.equals(ValueType.Char)) return env.assignVariable(name, new CharValue(value.charAt(0)));
                else if(type.equals(ValueType.String)) return env.assignVariable(name, new StringValue(value));
                else throw new InvalidConversionDataTypeException(ValueType.String, type);
            case IfStatement:
                return evaluateIfStatement((IfStatement) node, env);
            case ElseStatement:
                return new NullValue();
            default:
                throw new ASTNodeNotSetupException(node.kind());
        }
    }
}

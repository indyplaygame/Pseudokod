package indy.pseudokod.runtime;

import indy.pseudokod.ast.*;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ASTNodeNotSetupException;
import indy.pseudokod.exceptions.DivisionByZeroException;
import indy.pseudokod.exceptions.IncompatibleDataTypeException;
import indy.pseudokod.exceptions.MissingIdentifierException;
import indy.pseudokod.runtime.values.*;

import java.util.ArrayList;

public class Interpreter {

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

    static RuntimeValue evaluateIndexExpression(IndexExpression node, Environment env) throws Throwable {
        if(!(node.array().kind().equals(NodeType.Identifier) || node.array().kind().equals(NodeType.IndexExpression)))
            throw new MissingIdentifierException(NodeType.IndexExpression, node.array());

        final RuntimeValue left = evaluate(node.array(), env);

        if(!left.type().equals(ValueType.List)) throw new IncompatibleDataTypeException(ValueType.List, left.type());

        final ListValue array = (ListValue) left;
        final RuntimeValue index = evaluate(node.index(), env);

        return array.value().get(((int) ((NumberValue) index).value()) - 1);
    }

    static NumberValue evaluateNumericBinaryExpression(NumberValue left, NumberValue right, String operator) throws Throwable {
        double result = 0;

        if(operator.equals("+")) result = left.value() + right.value();
        else if(operator.equals("-")) result = left.value() - right.value();
        else if(operator.equals("*")) result = left.value() * right.value();
        else if(operator.equals("/")) {
            if(right.value() == 0) throw new DivisionByZeroException();
            result = left.value() / right.value();
        }
        else if(operator.equals("div")) result = Math.floor(left.value() / right.value());
        else if(operator.equals("mod")) result = left.value() % right.value();

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
            if(variable.value() != null) env.declareVariable(variable.symbol(), variable.type(), false, evaluate(variable.value(), env));
            else env.declareVariable(variable.symbol(), variable.type(), false, new NullValue());
        }

        return new NullValue();
    }

    static RuntimeValue evaluateAssignment(AssignmentExpression node, Environment env) throws Throwable {
        if(node.expression().kind() != NodeType.Identifier) throw new MissingIdentifierException(NodeType.AssignmentExpression, node.expression());

        return env.assignVariable(((Identifier) node.expression()).symbol(), evaluate(node.value(), env));
    }

    public static RuntimeValue evaluate(Statement node, Environment env) throws Throwable {
        switch(node.kind()) {
            case NumericLiteral:
                return new NumberValue(((NumericLiteral) node).value());
            case StringLiteral:
                return new StringValue(((StringLiteral) node).value());
            case CharacterLiteral:
                return new CharValue(((CharacterLiteral) node).value());
            case ArrayLiteral:
                ArrayList<RuntimeValue> values = new ArrayList<>();
                for(Expression expression : ((ArrayLiteral) node).values()) {
                    values.add(evaluate(expression, env));
                }
                return new ListValue(values);
            case Identifier:
                return evaluateIdentifier((Identifier) node, env);
            case BinaryExpression:
                return evaluateBinaryExpression((BinaryExpression) node, env);
            case IndexExpression:
                return evaluateIndexExpression((IndexExpression) node, env);
            case Program:
                return evaluateProgram((Program) node, env);
            case DataDeclaration:
                return evaluateDataDeclaration((DataDeclaration) node, env);
            case AssignmentExpression:
                return evaluateAssignment((AssignmentExpression) node, env);
            default:
                throw new ASTNodeNotSetupException(node.kind());
        }
    }
}

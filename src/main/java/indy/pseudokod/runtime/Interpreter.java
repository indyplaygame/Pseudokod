package indy.pseudokod.runtime;

import indy.pseudokod.ast.*;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ASTNodeNotSetupException;
import indy.pseudokod.exceptions.DivisionByZeroException;
import indy.pseudokod.exceptions.MissingIdentifierException;
import indy.pseudokod.runtime.values.*;

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
        else return new NullValue();
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

    static RuntimeValue evaluateIdentifier(Identifier node, Environment env) throws Throwable {
        return env.getVariable(node.symbol());
    }

    static RuntimeValue evaluateDataDeclaration(DataDeclaration node, Environment env) throws Throwable {
        for(Statement statement : node.body()) {
            VariableDeclaration variable = (VariableDeclaration) statement;
            if(variable.value() != null) env.declareVariable(variable.symbol(), evaluate(variable.value(), env));
            else env.declareVariable(variable.symbol(), new NullValue());
        }

        return new NullValue();
    }

    static RuntimeValue evaluateAssignment(AssignmentExpression node, Environment env) throws Throwable {
        if(node.expression().kind() != NodeType.Identifier) throw new MissingIdentifierException(node.expression());

        return env.assignVariable(((Identifier) node.expression()).symbol(), evaluate(node.value(), env));
    }

    public static RuntimeValue evaluate(Statement node, Environment env) throws Throwable {
        switch(node.kind()) {
            case NumericLiteral:
                return new NumberValue(((NumericLiteral) node).value());
            case Identifier:
                return evaluateIdentifier((Identifier) node, env);
            case BinaryExpression:
                return evaluateBinaryExpression((BinaryExpression) node, env);
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

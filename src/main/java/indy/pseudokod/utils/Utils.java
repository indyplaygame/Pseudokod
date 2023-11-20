package indy.pseudokod.utils;

import indy.pseudokod.ast.*;
import indy.pseudokod.exceptions.UnrecognizedStatementException;
import indy.pseudokod.lexer.Token;
import indy.pseudokod.runtime.values.*;

import java.util.List;

public class Utils {
    public static String stringifyTokens(List<Token> tokens) {
        String result = "[\n";

        for(Token token : tokens) {
            result += "\t{value: \"" + token.value() + "\", type: " + token.type() + "},\n";
        }

        result += "]";
        return result;
    }

    public static String stringifyProgram(Program program) throws UnrecognizedStatementException {
        String result = "{kind: " + program.kind() + ", body: [";

        for(Statement statement : program.body()) {
            result += stringifyStatement(statement);
        }

        result += "]}";
        return result;
    }

    public static String stringifyStatement(Statement statement) throws UnrecognizedStatementException {
        String result = "";

        switch(statement.kind()) {
            case NumericLiteral:
                result += stringifyNumber((NumericLiteral) statement);
                break;
            case Identifier:
                result += stringifyIdentifier((Identifier) statement);
                break;
            case BinaryExpression:
                result += "{\n" + stringifyBinaryExpression((BinaryExpression) statement) + "}";
                break;
            case DataDeclaration:
                result += "{" + stringifyDataDeclaration((DataDeclaration) statement) + "}";
                break;
            case VariableDeclaration:
                result += "\t{" + stringifyVariableDeclaration((VariableDeclaration) statement) + "}";
                break;
            default:
                throw new UnrecognizedStatementException(statement);
        }

        return result;
    }

    public static String stringifyNumber(NumericLiteral number) {
        return "{kind: " + number.kind() + ", value: \"" + number.value() + "\"},\n";
    }

    public static String stringifyIdentifier(Identifier identifier) {
        return "{kind: " + identifier.kind() + ", value: \"" + identifier.symbol() + "\"},\n";
    }

    public static String stringifyBinaryExpression(BinaryExpression expression) throws UnrecognizedStatementException {
        String result = "\tkind: \"" + expression.kind() + "\",\n" +
            "\tleft: " + stringifyStatement(expression.left()) +
            "\tright: " + stringifyStatement(expression.right()) +
            "\toperator: \"" + expression.operator() + "\"";

        return result;
    }

    public static String stringifyDataDeclaration(DataDeclaration expression) throws UnrecognizedStatementException {
        String result = "kind: \"" + expression.kind() + "\", body: [\n";

        for(Statement statement : expression.body()) {
            result += stringifyStatement(statement) + ",\n";
        }

        result += "]";

        return result;
    }

    public static String stringifyVariableDeclaration(VariableDeclaration expression) throws UnrecognizedStatementException {
        String result = "kind: \"" + expression.kind() + "\",type: " + expression.type() +", symbol: \"" + expression.symbol() + "\", constant: " + expression.constant() + ", value: ";

        if(expression.value() != null) {
            result += "{" + stringifyStatement(expression.value()) + "\n}";
        } else {
            result += "null";
        }

        return result;
    }

    public static String stringifyRuntimeValue(RuntimeValue value) {
        if(value.type() == ValueType.Number) return "{type: " + value.type() + ", value: \"" + ((NumberValue) value).value() + "\"}";
        else if(value.type() == ValueType.Boolean) return "{type: " + value.type() + ", value: \"" + ((BooleanValue) value).value() + "\"}";
        else if(value.type() == ValueType.NULL) return "{type: " + value.type() + ", value: \"" + ((NullValue) value).value() + "\"}";
        else return "{type: " + value.type() + "}";
    }
}

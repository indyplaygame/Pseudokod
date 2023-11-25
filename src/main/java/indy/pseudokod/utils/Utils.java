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
            result += "\t" + stringifyToken(token) + ",\n";
        }

        result += "]";
        return result;
    }

    public static String stringifyToken(Token token) {
        return "{value: \"" + token.value() + "\", type: " + token.type() + "}";
    }

    public static String stringifyProgram(Program program) throws Throwable {
        String result = "{kind: " + program.kind() + ", body: [";

        for(Statement statement : program.body()) {
            result += stringifyStatement(statement);
        }

        result += "]}";
        return result;
    }

    public static String stringifyStatement(Statement statement) throws Throwable {
        String result = "";

        switch(statement.kind()) {
            case NumericLiteral:
                result += stringifyNumber((NumericLiteral) statement);
                break;
            case CharacterLiteral:
                result += stringifyCharacter((CharacterLiteral) statement);
                break;
            case StringLiteral:
                result += stringifyString((StringLiteral) statement);
                break;
            case ArrayLiteral:
                result += stringifyArray((ArrayLiteral) statement);
                break;
            case SetLiteral:
                result += stringifySet((SetLiteral) statement);
                break;
            case Identifier:
                result += stringifyIdentifier((Identifier) statement);
                break;
            case ContinueStatement:
                result += stringifyContinueStatement((ContinueStatement) statement);
                break;
            case BinaryExpression:
                result += "{\n" + stringifyBinaryExpression((BinaryExpression) statement) + "}";
                break;
            case IndexExpression:
                result += stringifyIndexExpression((IndexExpression) statement);
                break;
            case DataDeclaration:
                result += "{" + stringifyDataDeclaration((DataDeclaration) statement) + "}";
                break;
            case VariableDeclaration:
                result += "\t{" + stringifyVariableDeclaration((VariableDeclaration) statement) + "}";
                break;
            case AssignmentExpression:
                result += "{" + stringifyAssignmentExpression((AssignmentExpression) statement) + "}";
                break;
            case CallExpression:
                result += stringifyCallExpression((CallExpression) statement);
                break;
            default:
                throw new UnrecognizedStatementException(statement);
        }

        return result;
    }

    public static String stringifyNumber(NumericLiteral number) {
        return "{kind: " + number.kind() + ", value: \"" + number.value() + "\"},\n";
    }

    public static String stringifyString(StringLiteral string) {
        return "{kind: " + string.kind() + ", value: \"" + string.value() + "\"},\n";
    }

    public static String stringifyCharacter(CharacterLiteral character) {
        return "{kind: " + character.kind() + ", value: \"" + character.value() + "\"},\n";
    }

    public static String stringifyArray(ArrayLiteral array) throws Throwable {
        String result = "kind: " + array.kind() + ", values: [\n";

        for(Expression expr : array.values()) {
            result += "\t\t" + stringifyStatement(expr);
        }

        return result;
    }

    public static String stringifySet(SetLiteral set) throws Throwable {
        String result = "kind: " + set.kind() + ", values: [\n";

        for(Expression expr : set.values()) {
            result += "\t\t" + stringifyStatement(expr);
        }

        return result;
    }

    public static String stringifyContinueStatement(ContinueStatement statement) throws Throwable {
        return "{kind: " + statement.kind() + "},\n";
    }

    public static String stringifyIndexExpression(IndexExpression expression) throws Throwable {
        return "{kind: " + expression.kind() + ", array: \"" + stringifyStatement(expression.array()) + "\", index: \"" + stringifyStatement(expression.index()) + "\"}";
    }

    public static String stringifyCallExpression(CallExpression expression) throws Throwable {
        String result = "{kind: " + expression.kind() + ", expression: " + stringifyStatement(expression.expression()) + ", args: [\n";

        for(Expression arg : expression.args()) {
            result += "{" + stringifyStatement(arg) + "}\n";
        }

        result += "]}";
        return result;
    }

    public static String stringifyIdentifier(Identifier identifier) {
        return "{kind: " + identifier.kind() + ", value: \"" + identifier.symbol() + "\"},\n";
    }

    public static String stringifyBinaryExpression(BinaryExpression expression) throws Throwable {
        return "\tkind: \"" + expression.kind() + "\",\n" +
            "\tleft: " + stringifyStatement(expression.left()) +
            "\tright: " + stringifyStatement(expression.right()) +
            "\toperator: \"" + expression.operator() + "\"";
    }

    public static String stringifyDataDeclaration(DataDeclaration expression) throws Throwable {
        String result = "kind: \"" + expression.kind() + "\", body: [\n";

        for(Statement statement : expression.body()) {
            result += stringifyStatement(statement) + ",\n";
        }

        result += "]";

        return result;
    }

    public static String stringifyVariableDeclaration(VariableDeclaration expression) throws Throwable {
        String result = "kind: \"" + expression.kind() + "\", type: " + expression.type() +", symbol: \"" + expression.symbol() + "\", constant: " + expression.constant() + ", value: ";

        if(expression.value() != null) {
            result += "{" + stringifyStatement(expression.value()) + "\n}";
        } else {
            result += "null";
        }

        return result;
    }

    public static String stringifyAssignmentExpression(AssignmentExpression expression) throws Throwable {
        return "kind: \"" + expression.kind() + "\", expression: " + stringifyStatement(expression.expression()) + "\", value: " + stringifyStatement(expression.value());
    }

    public static String stringifyRuntimeValue(RuntimeValue value) {
        if(value.type() == ValueType.Number) return "{type: " + value.type() + ", value: \"" + ((NumberValue) value).value() + "\"}";
        else if(value.type() == ValueType.Char) return "{type: " + value.type() + ", value: \"" + ((CharValue) value).value() + "\"}";
        else if(value.type() == ValueType.String) return "{type: " + value.type() + ", value: \"" + ((StringValue) value).value() + "\"}";
        else if(value.type() == ValueType.Boolean) return "{type: " + value.type() + ", value: \"" + ((BooleanValue) value).value() + "\"}";
        else if(value.type() == ValueType.List) {
            String result = "{type: " + value.type() + ", values: [\n";

            for(RuntimeValue val : ((ListValue) value).value()) {
                result += "\t" + stringifyRuntimeValue(val) + "\n";
            }

            return result + "]}";
        } else if(value.type() == ValueType.Set) {
            String result = "{type: " + value.type() + ", values: [\n";

            for(RuntimeValue val : ((SetValue) value).value()) {
                result += "\t" + stringifyRuntimeValue(val) + "\n";
            }

            return result + "]}";
        } else if(value.type() == ValueType.NULL) return "{type: " + value.type() + ", value: \"" + ((NullValue) value).value() + "\"}";
        else return "{type: " + value.type() + "}";
    }
}

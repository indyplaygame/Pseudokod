package indy.pseudokod.utils;

import indy.pseudokod.ast.*;
import indy.pseudokod.exceptions.UnrecognizedStatementException;
import indy.pseudokod.lexer.Token;
import indy.pseudokod.runtime.values.*;

import java.util.List;

public class Utils {

    private static int indent = 1;

    public static String stringifyTokens(List<Token> tokens) {
        StringBuilder result = new StringBuilder("[\n");

        for(Token token : tokens) {
            result.append("\t").append(stringifyToken(token)).append(",\n");
        }

        result.append("]");
        return result.toString();
    }

    public static String stringifyToken(Token token) {
        return "{value: \"" + token.value() + "\", type: " + token.type() + "}";
    }

    public static String stringifyProgram(Program program) throws Throwable {
        StringBuilder result = new StringBuilder("{kind: " + program.kind() + ", body: [\n");

        for(Statement statement : program.body()) {
            indent = 1;
            result.append((stringifyStatement(statement) + ",\n").indent(1));
        }

        result.append("\n]}");
        return result.toString();
    }

    public static String stringifyStatement(Statement statement) throws Throwable {

        switch(statement.kind()) {
            case NumericLiteral:
                return stringifyNumber((NumericLiteral) statement);
            case CharacterLiteral:
                return stringifyCharacter((CharacterLiteral) statement);
            case StringLiteral:
                return stringifyString((StringLiteral) statement);
            case ArrayLiteral:
                return stringifyArray((ArrayLiteral) statement);
            case SetLiteral:
                return stringifySet((SetLiteral) statement);
            case Identifier:
                return stringifyIdentifier((Identifier) statement);
            case ContinueStatement:
                return stringifyContinueStatement((ContinueStatement) statement);
            case BinaryExpression:
                return "{\n" + stringifyBinaryExpression((BinaryExpression) statement) + "}";
            case IndexExpression:
                return stringifyIndexExpression((IndexExpression) statement);
            case DataDeclaration:
                return stringifyDataDeclaration((DataDeclaration) statement);
            case VariableDeclaration:
                return "{" + stringifyVariableDeclaration((VariableDeclaration) statement) + "}";
            case FunctionDeclaration:
                return "{" + stringifyFunctionDeclaration((FunctionDeclaration) statement) + "}";
            case AssignmentExpression:
                return "{" + stringifyAssignmentExpression((AssignmentExpression) statement) + "}";
            case CallExpression:
                return stringifyCallExpression((CallExpression) statement);
            case PrintFunction:
                return stringifyPrintFunction((PrintFunction) statement);
            case GetFunction:
                return stringifyGetFunction((GetFunction) statement);
//            case IfStatement:
//                return stringifyIfStatement((IfStatement) statement);
            case ReturnStatement:
                return stringifyReturnStatement((ReturnStatement) statement);
            default:
                throw new UnrecognizedStatementException(statement);
        }
    }

    public static String stringifyNumber(NumericLiteral number) {
        return "{kind: " + number.kind() + ", value: \"" + number.value() + "\"},\n";
    }

    public static String stringifyPrintFunction(PrintFunction function) throws Throwable {
        StringBuilder result = new StringBuilder("{kind: " + function.kind() + ", args: [\n");

        indent++;
        for(Expression expression : function.args()) {
            result.append((stringifyStatement(expression) + ",\n").indent(indent));
        }

        indent--;
        return result + "]}".indent(indent);
    }

    public static String stringifyGetFunction(GetFunction function) throws Throwable {
        return  "{kind: " + function.kind() + ", identifier: \"" + function.identifier() + "\"}";
    }

//    public static String stringifyIfStatement(IfStatement statement) throws Throwable {
//        StringBuilder result = new StringBuilder("{kind: " + statement.kind() + ", body: [\n");
//
//        indent++;
//        for(Statement stmt : statement.body()) {
//            result.append(stringifyStatement(stmt).indent(indent));
//        }
//        indent--;
//        result.append("], else_body: [\n");
//        indent++;
//        for(Statement stmt : statement.else_body()) {
//            result.append(stringifyElseStatement(stmt).indent(indent));
//        }
//        indent--;
//
//        return result + "]}";
//    }

//    public static String stringifyElseStatement(Statement statement) throws Throwable {
//        StringBuilder result = new StringBuilder(("{kind: " + statement.kind() + ", ").indent(indent));
//
//        if(statement instanceof ElseStatement) {
//            result.append("body: [\n");
//            indent++;
//            for(Statement stmt : ((ElseStatement) statement).body()) {
//                result.append(stringifyStatement(stmt).indent(indent));
//            }
//            indent--;
//        } else if(statement instanceof IfStatement) {
//            result.append(stringifyIfStatement((IfStatement) statement));
//        }
//
//        return result + "},";
//    }

    public static String stringifyString(StringLiteral string) {
        return "{kind: " + string.kind() + ", value: \"" + string.value() + "\"}";
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

    public static String stringifyReturnStatement(ReturnStatement statement) throws Throwable {
        return "{kind: " + statement.kind() + ", value: " + stringifyStatement(statement.value()) + "}";
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
        StringBuilder result = new StringBuilder("{kind: \"" + expression.kind() + "\", body: [\n");

        indent++;
        for(Statement statement : expression.body()) {
            result.append((stringifyStatement(statement) + ",\n").indent(indent));
        }

        indent--;
        result.append("]}".indent(indent));

        return result.toString();
    }

    public static String stringifyVariableDeclaration(VariableDeclaration expression) throws Throwable {
        String result = "kind: \"" + expression.kind() + "\", type: " + expression.type() +", symbol: \"" + expression.symbol() + "\", constant: " + expression.constant() + ", range: " + expression.range() + ", value: ";

        if(expression.value() != null) {
            result += ("{" + stringifyStatement(expression.value()) + "\n}").indent(indent);
        } else {
            result += "null";
        }

        return result;
    }

    public static String stringifyFunctionDeclaration(FunctionDeclaration expression) throws Throwable {
        String result = "kind: " + expression.kind() + ", body: [\n";

        for(Statement statement : expression.body()) {
            result += stringifyStatement(statement).indent(indent);
        }

        result += "]";
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
            StringBuilder result = new StringBuilder("{type: " + value.type() + ", values: [\n");

            for(RuntimeValue val : ((SetValue) value).value()) {
                result.append("\t").append(stringifyRuntimeValue(val)).append("\n");
            }

            return result + "]}";
        } else if(value.type() == ValueType.Range) {
            RangeValue range = (RangeValue) value;
            return "{type: " + value.type() + ", value: " + (range.left_inclusive() ? "[" : "(") + range.left_bound() + ", " + range.right_bound() + (range.right_inclusive() ? "]" : ")") + "}";
        } else if(value.type() == ValueType.NULL) return "{type: " + value.type() + ", value: \"" + ((NullValue) value).value() + "\"}";
        else return "{type: " + value.type() + "}";
    }
}

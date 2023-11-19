package indy.pseudokod.utils;

import indy.pseudokod.ast.*;
import indy.pseudokod.exceptions.UnrecognizedStatementException;
import indy.pseudokod.lexer.Token;

import java.util.List;

public class Utils {
    public static void printStructure(List<Token> tokens) {
        System.out.println("[");
        for(Token token : tokens) {
            System.out.println("\t{value: \"" + token.value() + "\", type: " + token.type() + "},");
        }
        System.out.println("]");
    }

    public static String stringifyProgram(Program program) throws UnrecognizedStatementException {
        String result = "{kind: " + program.kind() + ", body: [";

        for(Statement statement : program.body()) {
            result += stringifyStatement(statement);
        }

        result += "\n]}";
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
}

package indy.pseudokod.lexer;
import indy.pseudokod.exceptions.UnrecognizedCharacterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("data", TokenType.DataToken);
        keywords.put("dane", TokenType.DataToken);
        keywords.put("number", TokenType.NumberType);
        keywords.put("liczba", TokenType.NumberType);
        keywords.put("char", TokenType.CharType);
        keywords.put("znak", TokenType.CharType);
        keywords.put("string", TokenType.StringType);
        keywords.put("tekst", TokenType.StringType);
        keywords.put("boolean", TokenType.BooleanType);
        keywords.put("logiczny", TokenType.BooleanType);
        keywords.put("list", TokenType.ListType);
        keywords.put("tablica", TokenType.ListType);
        keywords.put("range", TokenType.RangeType);
        keywords.put("przedzial", TokenType.RangeType);
        keywords.put("belongs", TokenType.InRange);
        keywords.put("in", TokenType.InRange);
        keywords.put("nalezy", TokenType.InRange);
        keywords.put("w", TokenType.InRange);
        keywords.put("OR", TokenType.OrOperator);
        keywords.put("LUB", TokenType.OrOperator);
        keywords.put("AND", TokenType.AndOperator);
        keywords.put("I", TokenType.AndOperator);
        keywords.put("NOT", TokenType.NotOperator);
        keywords.put("NIE", TokenType.NotOperator);
        keywords.put("mod", TokenType.ModulusOperator);
        keywords.put("div", TokenType.DivOperator);
    }

    public static Token[] tokenize(String source) throws Throwable {
        final ArrayList<Token> tokens = new ArrayList<>();
        final ArrayList<String> src = new ArrayList<>(List.of(source.split("")));
        final ArrayList<String> operators = new ArrayList<>(List.of(new String[]{"+", "-", "*", "/"}));

        while(src.size() > 0) {
            if(src.get(0).equals("(")) tokens.add(new Token(src.remove(0), TokenType.OpenParenthesis));
            else if(src.get(0).equals(")")) tokens.add(new Token(src.remove(0), TokenType.CloseParenthesis));
            else if(operators.contains(src.get(0))) tokens.add(new Token(src.remove(0), TokenType.BinaryOperator));
            else if(src.get(0).equals("=")) tokens.add(new Token(src.remove(0), TokenType.Equal));
            else if(src.get(0).equals("≠")) tokens.add(new Token(src.remove(0), TokenType.NotEqual));
            else if(src.get(0).equals("≤")) tokens.add(new Token(src.remove(0), TokenType.LessOrEqual));
            else if(src.get(0).equals("≥")) tokens.add(new Token(src.remove(0), TokenType.GreaterOrEqual));
            else if(src.get(0).equals("!")) {
                String value = src.remove(0);

                if(src.size() > 0 && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.NotEqual));
                } else throw new UnrecognizedCharacterException(src.get(0));
            } else if(src.get(0).equals("<")) {
                String value = src.remove(0);

                if(src.size() > 0 && src.get(0).equals("-")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.Assignment));
                } else if(src.size() > 0 && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.LessOrEqual));
                } else tokens.add(new Token(value, TokenType.LessThan));
            } else if(src.get(0).equals(">")) {
                String value = src.remove(0);

                if(src.size() > 0 && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.GreaterOrEqual));
                } else tokens.add(new Token(value, TokenType.GreaterThan));
            } else if(src.get(0).equals(":")) {
                String value = src.remove(0);

                if(src.size() > 0 && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.Assignment));
                } else tokens.add(new Token(value, TokenType.Colon));
            }
            else if(src.get(0).equals("∨")) tokens.add(new Token(src.remove(0), TokenType.OrOperator));
            else if(src.get(0).equals("∧")) tokens.add(new Token(src.remove(0), TokenType.AndOperator));
            else if(src.get(0).equals("¬") || src.get(0).equals("~")) tokens.add(new Token(src.remove(0), TokenType.AndOperator));
            else if(src.get(0).equals(";")) tokens.add(new Token(src.remove(0), TokenType.Semicolon));
            else if(src.get(0).equals(",")) tokens.add(new Token(src.remove(0), TokenType.Comma));
            else if(src.get(0).equals("∈")) tokens.add(new Token(src.remove(0), TokenType.InRange));
            else if(src.get(0).equals("\t")) {
                tokens.add(new Token("\\t", TokenType.Tabulator));
                src.remove(0);
            } else if(src.get(0).equals("\n")) {
                tokens.add(new Token("\\n", TokenType.NewLine));
                src.remove(0);
            } else {
                if(Character.isDigit(src.get(0).codePointAt(0))) {
                    String value = "";
                    while(src.size() > 0 && Character.isDigit(src.get(0).codePointAt(0))) {
                        value += src.remove(0);
                    }
                    tokens.add(new Token(value, TokenType.Number));
                } else if(Character.isAlphabetic(src.get(0).charAt(0))) {
                    String value = "";
                    while(src.size() > 0 && Character.isAlphabetic(src.get(0).codePointAt(0))) {
                        value += src.remove(0);
                    }
                    if(!keywords.containsKey(value)) tokens.add(new Token(value, TokenType.Identifier));
                    else tokens.add(new Token(value, keywords.get(value)));
                } else if(src.get(0).equals(" ")) src.remove(0);
                else throw new UnrecognizedCharacterException(src.get(0));
            }
        }
        tokens.add(new Token("EndOfFile", TokenType.EndOfFile));

        return tokens.toArray(new Token[0]);
    }
}

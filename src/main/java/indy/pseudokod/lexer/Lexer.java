package indy.pseudokod.lexer;

import indy.pseudokod.exceptions.StringTerminationException;
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
        keywords.put("number", TokenType.DataType);
        keywords.put("liczba", TokenType.DataType);
        keywords.put("char", TokenType.DataType);
        keywords.put("znak", TokenType.DataType);
        keywords.put("string", TokenType.DataType);
        keywords.put("tekst", TokenType.DataType);
        keywords.put("boolean", TokenType.DataType);
        keywords.put("logiczny", TokenType.DataType);
        keywords.put("list", TokenType.DataType);
        keywords.put("tablica", TokenType.DataType);
        keywords.put("set", TokenType.DataType);
        keywords.put("zbior", TokenType.DataType);
        keywords.put("range", TokenType.DataType);
        keywords.put("przedzial", TokenType.DataType);
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
        keywords.put("print", TokenType.PrintFunction);
        keywords.put("write", TokenType.PrintFunction);
        keywords.put("wypisz", TokenType.PrintFunction);
        keywords.put("get", TokenType.GetFunction);
        keywords.put("input", TokenType.GetFunction);
        keywords.put("wczytaj", TokenType.GetFunction);
        keywords.put("wprowadz", TokenType.GetFunction);
    }

    public static List<Token> tokenize(String source) throws Throwable {
        final ArrayList<Token> tokens = new ArrayList<>();
        final ArrayList<String> src = new ArrayList<>(List.of(source.split("")));
        final ArrayList<String> operators = new ArrayList<>(List.of(new String[]{"+", "-", "*"}));
        final ArrayList<String> ranges = new ArrayList<>(List.of(new String[]{"N", "Z", "Q"}));

        while(!src.isEmpty()) {
            if(src.get(0).equals("(")) tokens.add(new Token(src.remove(0), TokenType.OpenParenthesis));
            else if(src.get(0).equals(")")) tokens.add(new Token(src.remove(0), TokenType.CloseParenthesis));
            else if(src.get(0).equals("[")) tokens.add(new Token(src.remove(0), TokenType.OpenBracket));
            else if(src.get(0).equals("]")) tokens.add(new Token(src.remove(0), TokenType.CloseBracket));
            else if(src.get(0).equals("{")) tokens.add(new Token(src.remove(0), TokenType.OpenBrace));
            else if(src.get(0).equals("}")) tokens.add(new Token(src.remove(0), TokenType.CloseBrace));
            else if(operators.contains(src.get(0))) tokens.add(new Token(src.remove(0), TokenType.BinaryOperator));
            else if(src.get(0).equals("/")) {
                String value = src.remove(0);
                if(src.get(0).equals("/")) {
                    while(!src.get(0).equals("\n")) {
                        src.remove(0);
                    }
                } else if(src.get(0).equals("*")) {
                    src.remove(0);
                    while(!src.isEmpty() && !src.get(0).equals("*")) {
                        src.remove(0);
                    }
                    if(!src.isEmpty() && src.get(0).equals("*")) src.remove(0);
                    if(!src.isEmpty() && src.get(0).equals("/")) src.remove(0);
                } else tokens.add(new Token(value,TokenType.BinaryOperator));
            }
            else if(ranges.contains(src.get(0))) tokens.add(new Token(src.remove(0), TokenType.Range));
            else if(src.get(0).equals("=")) tokens.add(new Token(src.remove(0), TokenType.Equal));
            else if(src.get(0).equals("≠")) tokens.add(new Token(src.remove(0), TokenType.NotEqual));
            else if(src.get(0).equals("≤")) tokens.add(new Token(src.remove(0), TokenType.LessOrEqual));
            else if(src.get(0).equals("≥")) tokens.add(new Token(src.remove(0), TokenType.GreaterOrEqual));
            else if(src.get(0).equals("!")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.NotEqual));
                } else throw new UnrecognizedCharacterException(src.get(0));
            } else if(src.get(0).equals("<")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals("-")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.Assignment));
                } else if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.LessOrEqual));
                } else tokens.add(new Token(value, TokenType.LessThan));
            } else if(src.get(0).equals(">")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.GreaterOrEqual));
                } else tokens.add(new Token(value, TokenType.GreaterThan));
            } else if(src.get(0).equals(":")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.Assignment));
                } else tokens.add(new Token(value, TokenType.Colon));
            }
            else if(src.get(0).equals("∨")) tokens.add(new Token(src.remove(0), TokenType.OrOperator));
            else if(src.get(0).equals("∧")) tokens.add(new Token(src.remove(0), TokenType.AndOperator));
            else if(src.get(0).equals("¬") || src.get(0).equals("~")) tokens.add(new Token(src.remove(0), TokenType.AndOperator));
            else if(src.get(0).equals(";")) tokens.add(new Token(src.remove(0), TokenType.Semicolon));
            else if(src.get(0).equals(",")) tokens.add(new Token(src.remove(0), TokenType.Comma));
            else if(src.get(0).equals(".")) tokens.add(new Token(src.remove(0), TokenType.Dot));
            else if(src.get(0).equals("\"")) {
                tokens.add(new Token(src.remove(0), TokenType.Quote));
                StringBuilder value = new StringBuilder();
                if(!src.isEmpty() && !src.get(0).equals("\"")) {
                    while(!src.isEmpty() && !src.get(0).equals("\"")) {
                        if(src.get(0).equals("\\")) src.remove(0);
                        value.append(src.remove(0));
                    }
                }
                tokens.add(new Token(value.toString(), TokenType.Text));

                try {
                    if (src.get(0).equals("\"")) tokens.add(new Token(src.remove(0), TokenType.Quote));
                    else throw new StringTerminationException('\"');
                } catch(IndexOutOfBoundsException e) {
                    throw new StringTerminationException('\"');
                }

            } else if(src.get(0).equals("'")) {
                tokens.add(new Token(src.remove(0), TokenType.Apostrophe));
                if(!src.isEmpty() && !src.get(0).equals("''")) {
                    StringBuilder value = new StringBuilder();
                    while(!src.isEmpty() && !src.get(0).equals("'")) {
                        if(src.get(0).equals("\\")) src.remove(0);
                        value.append(src.remove(0));
                    }
                    tokens.add(new Token(value.toString(), TokenType.Character));
                }

                try {
                    if (src.get(0).equals("'")) tokens.add(new Token(src.remove(0), TokenType.Apostrophe));
                    else throw new StringTerminationException('\'');
                } catch(IndexOutOfBoundsException e) {
                    throw new StringTerminationException('\"');
                }

            } else if(src.get(0).equals("∈")) tokens.add(new Token(src.remove(0), TokenType.InRange));
            else if(src.get(0).equals("∞")) tokens.add(new Token(src.remove(0), TokenType.Identifier));
            else if(src.get(0).equals("\t")) {
                tokens.add(new Token("\\t", TokenType.Tabulator));
                src.remove(0);
            } else if(src.get(0).equals("\n")) {
                tokens.add(new Token("\\n", TokenType.NewLine));
                src.remove(0);
            } else {
                if(Character.isDigit(src.get(0).codePointAt(0))) {
                    StringBuilder value = new StringBuilder();
                    while(!src.isEmpty() && (Character.isDigit(src.get(0).codePointAt(0)) || src.get(0).equals("."))) {
                        value.append(src.remove(0));
                    }
                    tokens.add(new Token(value.toString(), TokenType.Number));
                } else if(Character.isAlphabetic(src.get(0).charAt(0))) {
                    StringBuilder value = new StringBuilder();
                    while(!src.isEmpty() && Character.isAlphabetic(src.get(0).codePointAt(0))) {
                        value.append(src.remove(0));
                    }

                    tokens.add(new Token(value.toString(), keywords.getOrDefault(value.toString(), TokenType.Identifier)));
                } else if(src.get(0).equals(" ")) src.remove(0);
                else {
                    throw new UnrecognizedCharacterException(src.get(0));
                }
            }
        }
        tokens.add(new Token("EndOfFile", TokenType.EndOfFile));

        return tokens;
    }
}

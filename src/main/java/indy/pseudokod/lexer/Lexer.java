package indy.pseudokod.lexer;

import indy.pseudokod.exceptions.StringTerminationException;
import indy.pseudokod.exceptions.UnrecognizedCharacterException;

import java.util.*;

/**
 * The {@link Lexer} provides functionality to tokenize a source string into meaningful tokens.
 * This is a fundamental step in the interpretation or compilation of source code.
 */
public class Lexer {
    private static final Map<String, TokenType> keywords = new HashMap<>();

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
        keywords.put("plate", TokenType.DataType);
        keywords.put("talerz", TokenType.DataType);
        keywords.put("queue", TokenType.DataType);
        keywords.put("kolejka", TokenType.DataType);
        keywords.put("belongs", TokenType.InRange);
        keywords.put("in", TokenType.InRange);
        keywords.put("nalezy", TokenType.InRange);
        keywords.put("w", TokenType.InRange);
        keywords.put("OR", TokenType.LogicalOperator);
        keywords.put("LUB", TokenType.LogicalOperator);
        keywords.put("AND", TokenType.LogicalOperator);
        keywords.put("I", TokenType.LogicalOperator);
        keywords.put("NOT", TokenType.LogicalOperator);
        keywords.put("NIE", TokenType.LogicalOperator);
        keywords.put("XOR", TokenType.LogicalOperator);
        keywords.put("mod", TokenType.ModulusOperator);
        keywords.put("div", TokenType.DivOperator);
        keywords.put("print", TokenType.PrintFunction);
        keywords.put("write", TokenType.PrintFunction);
        keywords.put("wypisz", TokenType.PrintFunction);
        keywords.put("drukuj", TokenType.PrintFunction);
        keywords.put("wyprintuj", TokenType.PrintFunction);
        keywords.put("get", TokenType.GetFunction);
        keywords.put("input", TokenType.GetFunction);
        keywords.put("wczytaj", TokenType.GetFunction);
        keywords.put("wprowadz", TokenType.GetFunction);
        keywords.put("if", TokenType.IfStatement);
        keywords.put("jezeli", TokenType.IfStatement);
        keywords.put("jesli", TokenType.IfStatement);
        keywords.put("else", TokenType.ElseStatement);
        keywords.put("przeciwnie", TokenType.ElseStatement);
        keywords.put("for", TokenType.ForStatement);
        keywords.put("dla", TokenType.ForStatement);
        keywords.put("while", TokenType.WhileStatement);
        keywords.put("dopoki", TokenType.WhileStatement);
        keywords.put("do", TokenType.DoStatement);
        keywords.put("rob", TokenType.DoStatement);
        keywords.put("function", TokenType.Function);
        keywords.put("funkcja", TokenType.Function);
        keywords.put("result", TokenType.ResultToken);
        keywords.put("wynik", TokenType.ResultToken);
        keywords.put("return", TokenType.ReturnToken);
        keywords.put("zwroc", TokenType.ReturnToken);
        keywords.put("import", TokenType.ImportToken);
        keywords.put("zaimportuj", TokenType.ImportToken);
    }

    /**
     * Converts the input source string into a list of tokens by analyzing its content.
     *
     * @param source The source string to be tokenized.
     * @return A list of {@link Token} objects representing the tokens in the source.
     * @throws StringTerminationException Thrown if a string literal is not properly terminated with a quote character.
     * @throws UnrecognizedCharacterException Thrown if an unrecognizable character is encountered in the source.
     */
    public static List<Token> tokenize(String source) throws UnrecognizedCharacterException, StringTerminationException {
        final ArrayList<Token> tokens = new ArrayList<>();
        final ArrayList<String> src = new ArrayList<>(List.of(source.split("")));
        final ArrayList<String> operators = new ArrayList<>(List.of(new String[]{"+", "-", "*"}));
        int line = 1;

        while(!src.isEmpty()) {
            if(src.get(0).equals("(")) tokens.add(new Token(src.remove(0), TokenType.OpenParenthesis, line));
            else if(src.get(0).equals(")")) tokens.add(new Token(src.remove(0), TokenType.CloseParenthesis, line));
            else if(src.get(0).equals("[")) tokens.add(new Token(src.remove(0), TokenType.OpenBracket, line));
            else if(src.get(0).equals("]")) tokens.add(new Token(src.remove(0), TokenType.CloseBracket, line));
            else if(src.get(0).equals("{")) tokens.add(new Token(src.remove(0), TokenType.OpenBrace, line));
            else if(src.get(0).equals("}")) tokens.add(new Token(src.remove(0), TokenType.CloseBrace, line));
            else if(operators.contains(src.get(0))) tokens.add(new Token(src.remove(0), TokenType.BinaryOperator, line));
            else if(src.get(0).equals("/")) {
                String value = src.remove(0);
                if(src.get(0).equals("/")) {
                    while(!src.get(0).equals("\n")) {
                        src.remove(0);
                    }
                } else if(src.get(0).equals("*")) {
                    do src.remove(0);
                    while (!src.isEmpty() && !src.get(0).equals("*"));

                    if(!src.isEmpty() && src.get(0).equals("*")) src.remove(0);
                    if(!src.isEmpty() && src.get(0).equals("/")) src.remove(0);
                } else tokens.add(new Token(value, TokenType.BinaryOperator, line));
            }
//            else if(ranges.contains(src.get(0))) tokens.add(new Token(src.remove(0), TokenType.Range));
            else if(src.get(0).equals("=")) tokens.add(new Token(src.remove(0), TokenType.Equals, line));
            else if(src.get(0).equals("≠")) tokens.add(new Token(src.remove(0), TokenType.ComparisonOperator, line));
            else if(src.get(0).equals("≤")) tokens.add(new Token(src.remove(0), TokenType.ComparisonOperator, line));
            else if(src.get(0).equals("≥")) tokens.add(new Token(src.remove(0), TokenType.ComparisonOperator, line));
            else if(src.get(0).equals("!")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.ComparisonOperator, line));
                } else throw new UnrecognizedCharacterException(src.get(0));
            } else if(src.get(0).equals("<")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals("-")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.Assignment, line));
                } else if(!src.isEmpty() && src.get(0).equals("<")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.ShiftOperator, line));
                } else if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.ComparisonOperator, line));
                } else tokens.add(new Token(value, TokenType.ComparisonOperator, line));
            } else if(src.get(0).equals(">")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals(">")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.ShiftOperator, line));
                } else if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.ComparisonOperator, line));
                } else tokens.add(new Token(value, TokenType.ComparisonOperator, line));
            } else if(src.get(0).equals(":")) {
                String value = src.remove(0);

                if(!src.isEmpty() && src.get(0).equals("=")) {
                    value += src.remove(0);
                    tokens.add(new Token(value, TokenType.Assignment, line));
                } else tokens.add(new Token(value, TokenType.Colon, line));
            }
            else if(src.get(0).equals("∨")) tokens.add(new Token(src.remove(0), TokenType.LogicalOperator, line));
            else if(src.get(0).equals("∧")) tokens.add(new Token(src.remove(0), TokenType.LogicalOperator, line));
            else if(src.get(0).equals("¬")) tokens.add(new Token(src.remove(0), TokenType.LogicalOperator, line));
            else if(src.get(0).equals("⊕")) tokens.add(new Token(src.remove(0), TokenType.LogicalOperator, line));
            else if(src.get(0).equals("~")) tokens.add(new Token(src.remove(0), TokenType.BitwiseOperator, line));
            else if(src.get(0).equals("&")) tokens.add(new Token(src.remove(0), TokenType.BitwiseOperator, line));
            else if(src.get(0).equals("|")) tokens.add(new Token(src.remove(0), TokenType.BitwiseOperator, line));
            else if(src.get(0).equals("^")) tokens.add(new Token(src.remove(0), TokenType.BitwiseOperator, line));
            else if(src.get(0).equals(";")) tokens.add(new Token(src.remove(0), TokenType.Semicolon, line));
            else if(src.get(0).equals(",")) tokens.add(new Token(src.remove(0), TokenType.Comma, line));
            else if(src.get(0).equals(".")) tokens.add(new Token(src.remove(0), TokenType.Dot, line));
            else if(src.get(0).equals("\"")) {
                tokens.add(new Token(src.remove(0), TokenType.Quote, line));
                StringBuilder value = new StringBuilder();
                if(!src.isEmpty() && !src.get(0).equals("\"")) {
                    while(!src.isEmpty() && !src.get(0).equals("\"")) {
                        if(src.get(0).equals("\\")) value.append(src.remove(0));
                        value.append(src.remove(0));
                    }
                }
                tokens.add(new Token(value.toString(), TokenType.Text, line));

                try {
                    if (src.get(0).equals("\"")) tokens.add(new Token(src.remove(0), TokenType.Quote, line));
                    else throw new StringTerminationException('\"');
                } catch(IndexOutOfBoundsException e) {
                    throw new StringTerminationException('\"');
                }

            } else if(src.get(0).equals("'")) {
                tokens.add(new Token(src.remove(0), TokenType.Apostrophe, line));
                if(!src.isEmpty() && !src.get(0).equals("''")) {
                    StringBuilder value = new StringBuilder();
                    while(!src.isEmpty() && !src.get(0).equals("'")) {
                        if(src.get(0).equals("\\")) src.remove(0);
                        value.append(src.remove(0));
                    }
                    tokens.add(new Token(value.toString(), TokenType.Character, line));
                }

                try {
                    if (src.get(0).equals("'")) tokens.add(new Token(src.remove(0), TokenType.Apostrophe, line));
                    else throw new StringTerminationException('\'');
                } catch(IndexOutOfBoundsException e) {
                    throw new StringTerminationException('\"');
                }

            } else if(src.get(0).equals("∈")) tokens.add(new Token(src.remove(0), TokenType.InRange, line));
            else if(src.get(0).equals("∞")) tokens.add(new Token(src.remove(0), TokenType.Identifier, line));
            else if(src.get(0).charAt(0) == 9) {
                tokens.add(new Token("\\t", TokenType.Indent, line));
                src.remove(0);
            } else if(src.get(0).equals("\n")) {
                tokens.add(new Token("\\n", TokenType.NewLine, line));
                line++;
                src.remove(0);
            } else if(src.get(0).equals("\r")) {
                src.remove(0);
            } else {
                if(Character.isDigit(src.get(0).codePointAt(0))) {
                    StringBuilder value = new StringBuilder();
                    while(!src.isEmpty() && (Character.isDigit(src.get(0).codePointAt(0)) || src.get(0).equals("."))) {
                        value.append(src.remove(0));
                    }
                    tokens.add(new Token(value.toString(), TokenType.Number, line));
                } else if(Character.isAlphabetic(src.get(0).charAt(0))) {
                    StringBuilder value = new StringBuilder();
                    while(!src.isEmpty() && (Character.isAlphabetic(src.get(0).codePointAt(0)) || src.get(0).equals("_") || src.get(0).equals("*"))) {
                        value.append(src.remove(0));
                    }
                    tokens.add(new Token(value.toString(), keywords.getOrDefault(value.toString(), TokenType.Identifier), line));
                } else if(src.get(0).equals(" ")) {
                    src.remove(0);
                    if(!src.isEmpty() && src.get(0).equals(" ")) {
                        int length = 1;
                        while(!src.isEmpty() && src.get(0).equals(" ") && length < 4) {
                            src.remove(0);
                            length++;
                        }
                        tokens.add(new Token("\\t", TokenType.Indent, line));
                    }
                }
                else {
                    throw new UnrecognizedCharacterException(src.get(0));
                }
            }
        }
        tokens.add(new Token("EndOfFile", TokenType.EndOfFile, line));

        return tokens;
    }
}

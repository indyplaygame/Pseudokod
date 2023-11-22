package indy.pseudokod.parser;

import indy.pseudokod.ast.*;
import indy.pseudokod.exceptions.*;
import indy.pseudokod.lexer.Token;
import indy.pseudokod.lexer.TokenType;
import indy.pseudokod.runtime.values.ValueType;
import indy.pseudokod.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static indy.pseudokod.lexer.Lexer.tokenize;

public class Parser {
    private List<Token> tokens = new ArrayList<>();
    static final Map<String, ValueType> var_types = new HashMap<>();

    static {
        var_types.put("number", ValueType.Number);
        var_types.put("liczba", ValueType.Number);
        var_types.put("char", ValueType.Char);
        var_types.put("znak", ValueType.Char);
        var_types.put("string", ValueType.String);
        var_types.put("tekst", ValueType.String);
        var_types.put("boolean", ValueType.Boolean);
        var_types.put("logiczny", ValueType.Boolean);
        var_types.put("list", ValueType.List);
        var_types.put("tablica", ValueType.List);
        var_types.put("set", ValueType.Set);
        var_types.put("zbior", ValueType.Set);;
        var_types.put("range", ValueType.Range);
        var_types.put("przedzial", ValueType.Range);
    }

    private boolean isNotEOF() {
        return this.tokens.get(0).type() != TokenType.EndOfFile;
    }

    private Token at() {
        return this.tokens.get(0);
    }

    private Token eat() {
        return this.tokens.remove(0);
    }

    private void removeSkippable() {
        while(!tokens.isEmpty() && (this.at().type() == TokenType.NewLine || this.at().type() == TokenType.Tabulator)) {
            this.eat();
        }
    }

    private Token expect(TokenType type) throws MissingTokenException {
        if(tokens.isEmpty()) throw new MissingTokenException(type);
        final Token prev = this.eat();

        if(prev != null && prev.type() == type) return prev;
        else throw new MissingTokenException(type, prev.type());
    }

    private Statement parseStatement() throws Throwable {
        this.removeSkippable();
        final TokenType type = this.at().type();

        switch(type) {
            case DataToken:
                return this.parseDataDeclarationStatement();
            default:
                return this.parseExpression();
        }
    }

    private Statement parseDataDeclarationStatement() throws Throwable {
        this.eat();
        this.expect(TokenType.Colon);
        this.removeSkippable();

        List<Statement> statements = new ArrayList<>();
        Token data_type;
        Token identifier;
        Expression value;

        do {
            if(this.at().type() == TokenType.Comma) this.expect(TokenType.Comma);
            this.removeSkippable();

            data_type = this.expect(TokenType.DataType);
            identifier = this.expect(TokenType.Identifier);

            if(!tokens.isEmpty() && this.at().type() == TokenType.InRange) {
                if(!(data_type.value().equals("number") || data_type.value().equals("liczba"))) throw new IllegalDataTypeException(data_type.value());

                this.eat();
                Token range = this.expect(TokenType.Range);
            }

            if(!tokens.isEmpty() && this.at().type() == TokenType.Assignment) {
                this.eat();
                    value = this.parseExpression();

                    statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false, value));
            } else {
                statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false));
            }
        } while(this.at().type() == TokenType.Comma);
        this.expect(TokenType.Semicolon);

        return new DataDeclaration(statements);
    }

    private Expression parseAssignmentExpression(boolean expect_semicolon) throws Throwable {
        Expression left = parseAdditiveExpression();

        if(this.at().type() == TokenType.Assignment) {
            this.eat();

            final Expression value = this.parseAssignmentExpression(false);
            if(expect_semicolon) this.expect(TokenType.Semicolon);

            return new AssignmentExpression(left, value);
        }

        return left;
    }

    private Expression parseExpression() throws Throwable {
        return parseAssignmentExpression(true);
    }

    private Expression parseAdditiveExpression() throws Throwable {
        Expression left = parseMultiplicativeExpression();

        while (this.at().value().equals("+") || this.at().value().equals("-")) {
            String operator = this.eat().value();
            Expression right = parseMultiplicativeExpression();
            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseMultiplicativeExpression() throws Throwable {
        Expression left = parseMemberExpression();

        while(this.at().value().equals("*") || this.at().value().equals("/") || this.at().value().equals("mod") || this.at().value().equals("div")) {
            String operator = this.eat().value();
            Expression right = parseMemberExpression();
            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseMemberExpression() throws Throwable {
        Expression array = parsePrimaryExpression();

        while(this.at().type().equals(TokenType.OpenBracket)) {
            this.eat();

            final Expression index = parseExpression();

            if(!(index.kind().equals(NodeType.NumericLiteral) || index.kind().equals(NodeType.Identifier)))
                throw new IllegalIndexTypeException(index.kind());

            this.expect(TokenType.CloseBracket);

            array = new IndexExpression(array, index);
        }
        return array;
    }

    private Expression parsePrimaryExpression() throws Throwable {
        final TokenType token = this.at().type();

        switch(token) {
            case Identifier:
                return new Identifier(this.eat().value());
            case Number:
                return new NumericLiteral(this.eat().value());
            case Quote:
                this.eat();
                final String text = this.expect(TokenType.Text).value();
                this.expect(TokenType.Quote);

                return new StringLiteral(text);
            case Apostrophe:
                this.eat();
                final String character = this.expect(TokenType.Character).value();
                this.expect(TokenType.Apostrophe);

                if(character.length() > 1) throw new CharactersAmountException(character.length());

                return new CharacterLiteral(character.charAt(0));
            case OpenBracket:
                this.eat();

                ArrayList<Expression> values = new ArrayList<>();
                while(isNotEOF() && !this.at().type().equals(TokenType.CloseBracket)) {
                    values.add(parseExpression());
                    if(this.at().type().equals(TokenType.Comma)) this.eat();
                }

                this.expect(TokenType.CloseBracket);
                return new ArrayLiteral(values);
            case OpenParenthesis:
                this.eat();
                final Expression value = parseExpression();
                this.expect(TokenType.CloseParenthesis);

                return value;
            case BinaryOperator:
                String operator = this.eat().value();

                return new BinaryExpression(new NumericLiteral("0"), new NumericLiteral(this.eat().value()), operator);
            default:
                throw new UnexpectedTokenException(this.at());
        }
    }

    public Program produceAST(String source) throws Throwable {
        this.tokens = tokenize(source);
        final Program program = new Program(new ArrayList<>());

        while(isNotEOF()) {
            program.body().add(this.parseStatement());
        }

        return program;
    }
}

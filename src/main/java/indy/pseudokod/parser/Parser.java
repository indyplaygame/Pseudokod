package indy.pseudokod.parser;

import indy.pseudokod.ast.*;
import indy.pseudokod.exceptions.*;
import indy.pseudokod.lexer.Token;
import indy.pseudokod.lexer.TokenType;
import indy.pseudokod.runtime.values.ValueType;

import java.util.*;

import static indy.pseudokod.lexer.Lexer.tokenize;

public class Parser {
    private List<Token> tokens = new ArrayList<>();
    static final Map<String, ValueType> var_types = new HashMap<>();
    private static int indent = 0;

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
        var_types.put("zbior", ValueType.Set);
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
        while(!tokens.isEmpty() && (this.at().type() == TokenType.NewLine || this.at().type() == TokenType.Indent)) {
            this.eat();
        }
    }

    private Token expect(TokenType type) throws MissingTokenException {
        if(tokens.isEmpty()) throw new MissingTokenException(type);
        final Token prev = this.eat();

        if(prev != null && prev.type() == type) return prev;
        else throw new MissingTokenException(type, prev.type());
    }

    private boolean expect(TokenType type, int n) {
        for(int i = 0; i < n; i++) {
            if(!this.at().type().equals(type)) return false;
            this.eat();
        }
        return true;
    }

    private Token expect(TokenType... types) throws MissingTokenException {
        if(Arrays.stream(types).anyMatch(t -> this.at().type().equals(t))) return this.eat();
        else throw new MissingTokenException(this.at().type(), types);
    }

    private Statement parseStatement() throws Throwable {
        this.removeSkippable();
        final TokenType type = this.at().type();

        switch(type) {
            case DataToken:
                return this.parseDataDeclarationStatement();
            case PrintFunction:
                this.eat();
                if(this.at().type().equals(TokenType.Semicolon)) throw new UnexpectedTokenException(this.at());

                List<Expression> args = new ArrayList<>();
                args.add(parseExpression());

                while(this.isNotEOF() && this.at().type().equals(TokenType.Comma)) {
                    this.expect(TokenType.Comma);
                    args.add(parseExpression());
                }

                return new PrintFunction(args);
            case IfStatement: {
                indent++;
                this.eat();

                Expression expression = parseExpression();
                List<Statement> body = new ArrayList<>();

                while(this.isNotEOF() && this.at().type().equals(TokenType.NewLine)) {
                    this.expect(TokenType.NewLine);
                    if(!this.expect(TokenType.Indent, indent)) break;
                    body.add(parseStatement());
                }
                indent--;

                if(this.isNotEOF() && this.at().type().equals(TokenType.ElseStatement)) return new IfStatement(expression, body, parseStatement());

                return new IfStatement(expression, body); }
            case ElseStatement:
                this.eat();

                Expression expression = null;
                List<Statement> body = new ArrayList<>();
                boolean elseif = this.at().type().equals(TokenType.IfStatement);

                indent++;
                if(this.isNotEOF() && this.at().type().equals(TokenType.IfStatement)) {
                    this.eat();
                    expression = parseExpression();
                }

                while(this.isNotEOF() && this.at().type().equals(TokenType.NewLine)) {
                    this.expect(TokenType.NewLine);
                    if(!this.expect(TokenType.Indent, indent)) break;
                    body.add(parseStatement());
                }
                indent--;

                if(elseif) return new IfStatement(expression, body);
                else return new ElseStatement(body);
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

        do {
            if(this.at().type() == TokenType.Comma) this.expect(TokenType.Comma);
            this.removeSkippable();

            data_type = this.expect(TokenType.DataType);
            identifier = this.expect(TokenType.Identifier);
            RangeLiteral range = null;
            Expression value = null;

            if(!tokens.isEmpty() && this.at().type() == TokenType.InRange) {
                if(!(data_type.value().equals("number") || data_type.value().equals("liczba"))) throw new IllegalDataTypeException(data_type.value());

                this.eat();

                boolean left_included = this.expect(TokenType.OpenParenthesis, TokenType.OpenBracket).type().equals(TokenType.OpenBracket);
                Expression left = parseExpression();
                this.expect(TokenType.Comma);
                Expression right = parseExpression();
                boolean right_included = this.expect(TokenType.CloseParenthesis, TokenType.CloseBracket).type().equals(TokenType.CloseBracket);

                range = new RangeLiteral(left, right, left_included, right_included);
            }

            if(!tokens.isEmpty() && this.at().type() == TokenType.Assignment) {
                this.eat();
                value = this.parseExpression();
            }

            if(range == null && value == null) {
                statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false));
            } else if(range == null) {
                statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false, value));
            } else if(value == null) {
                statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false, range));
            } else {
                statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false, range, value));
            }
        } while(this.at().type() == TokenType.Comma);

        return new DataDeclaration(statements);
    }

    private Expression parseExpression() throws Throwable {
        return parseAssignmentExpression(true);
    }

    private Expression parseAssignmentExpression(boolean expect_semicolon) throws Throwable {
        Expression left = parseLogicalExpression();

        if(this.at().type() == TokenType.Assignment) {
            this.eat();

            final Expression value = this.parseAssignmentExpression(false);

            return new AssignmentExpression(left, value);
        }

        return left;
    }

    private Expression parseLogicalExpression() throws Throwable {
        Expression left = parseComparisonExpression();

        while(this.at().type().equals(TokenType.LogicalOperator)) {
            String operator = this.eat().value();
            Expression right = parseComparisonExpression();
            left = new LogicalExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseComparisonExpression() throws Throwable {
        Expression left = parseAdditiveExpression();

        while(this.at().type().equals(TokenType.ComparisonOperator)) {
            String operator = this.eat().value();
            Expression right = parseAdditiveExpression();
            left = new ComparisonExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseAdditiveExpression() throws Throwable {
        Expression left = parseMultiplicativeExpression();

        while(this.at().value().equals("+") || this.at().value().equals("-")) {
            String operator = this.eat().value();
            Expression right = parseMultiplicativeExpression();
            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseMultiplicativeExpression() throws Throwable {
        Expression left = parseCallIndexExpression();

        while(this.at().value().equals("*") || this.at().value().equals("/") || this.at().value().equals("mod") || this.at().value().equals("div")) {
            String operator = this.eat().value();
            Expression right = parseCallIndexExpression();
            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseCallIndexExpression() throws Throwable {
        Expression expression = parseIndexExpression();

        if(this.at().type().equals(TokenType.OpenParenthesis)) {
            return parseCallExpression(expression);
        }

        return expression;
    }

    private Expression parseIndexExpression() throws Throwable {
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

    private Expression parseCallExpression(Expression expression) throws Throwable {
        final Expression call = new CallExpression(expression, parseArguments());

        return call;
    }

    private List<Expression> parseArguments() throws Throwable {
        this.expect(TokenType.OpenParenthesis);
        final List<Expression> args = this.at().type().equals(TokenType.CloseParenthesis) ? new ArrayList<>() : this.parseArgumentsList();
        this.expect(TokenType.CloseParenthesis);

        return args;
    }

    private List<Expression> parseArgumentsList() throws Throwable {
        final List<Expression> args = new ArrayList<>();
        args.add(parseExpression());

        while(this.isNotEOF() && this.at().type().equals(TokenType.Comma)) {
            this.eat();
            args.add(parseExpression());
        }

        return args;
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
            case OpenBracket: {
                this.eat();

                ArrayList<Expression> values = new ArrayList<>();
                if(!this.at().type().equals(TokenType.CloseBracket)) values.add(parseExpression());

                while(isNotEOF() && !this.at().type().equals(TokenType.CloseBracket)) {
                    this.expect(TokenType.Comma);
                    values.add(parseExpression());
                }

                this.expect(TokenType.CloseBracket);
                return new ArrayLiteral(values); }
            case OpenBrace:
                this.eat();

                ArrayList<Expression> values = new ArrayList<>();
                if(!this.at().type().equals(TokenType.CloseBrace)) values.add(parseExpression());

                while(isNotEOF() && !this.at().type().equals(TokenType.CloseBrace)) {
                    this.expect(TokenType.Comma);

                    if(isNotEOF() && this.at().type().equals(TokenType.Dot)) {
                        this.eat();
                        this.expect(TokenType.Dot);
                        this.expect(TokenType.Dot);

                        values.add(new ContinueStatement());
                    } else {
                        values.add(parseExpression());
                    }
                }

                this.expect(TokenType.CloseBrace);
                return new SetLiteral(values);
            case OpenParenthesis:
                this.eat();
                final Expression value = parseExpression();
                this.expect(TokenType.CloseParenthesis);

                return value;
            case BinaryOperator: {
                String operator = this.eat().value();

                return new BinaryExpression(new NumericLiteral("0"), new NumericLiteral(this.eat().value()), operator); }
            case LogicalOperator:
                String operator = this.eat().value();

                if(!(operator.equals("NOT") || operator.equals("NIE") || operator.equals("~") || operator.equals("¬")))
                    throw new IllegalExpressionStartException(ValueType.Boolean);

                return new LogicalExpression(parseExpression(), operator);
            case GetFunction:
                this.eat();
                String identifier = this.expect(TokenType.Identifier).value();

                return new GetFunction(identifier);
            default:
                throw new UnexpectedTokenException(this.at());
        }
    }

    public Program produceAST(String source) throws Throwable {
        this.tokens = tokenize(source);
        final Program program = new Program(new ArrayList<>());

        while(isNotEOF()) {
            program.body().add(this.parseStatement());
            this.expect(TokenType.Semicolon);
        }

        return program;
    }
}

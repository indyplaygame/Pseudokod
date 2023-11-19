package indy.pseudokod.parser;

import indy.pseudokod.ast.*;
import indy.pseudokod.exceptions.MissingTokenException;
import indy.pseudokod.exceptions.UnexpectedTokenException;
import indy.pseudokod.lexer.Token;
import indy.pseudokod.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

import static indy.pseudokod.lexer.Lexer.tokenize;

public class Parser {
    private List<Token> tokens = new ArrayList<>();

    private boolean isNotEOF() {
        return this.tokens.get(0).type() != TokenType.EndOfFile;
    }

    private Token at() {
        return this.tokens.get(0);
    }

    private Token eat() {
        return this.tokens.remove(0);
    }

    private Token expect(TokenType type) throws MissingTokenException {
        if(!(tokens.size() > 0)) throw new MissingTokenException(type);
        final Token prev = this.eat();

        if(prev != null && prev.type() == type) return prev;
        else throw new MissingTokenException(type, prev.type());
    }

    private Statement parseStatement() throws Throwable {
        return this.parseExpression();
    }

    private Expression parseExpression() throws Throwable {
        return this.parseAdditiveExpression();
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
        Expression left = parsePrimaryExpression();

        while(this.at().value().equals("*") || this.at().value().equals("/") || this.at().value().equals("mod") || this.at().value().equals("div")) {
            String operator = this.eat().value();
            Expression right = parsePrimaryExpression();
            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parsePrimaryExpression() throws Throwable {
        final TokenType token = this.at().type();

        switch(token) {
            case Identifier:
                return new Identifier(this.eat().value());
            case Number:
                return new NumericLiteral(this.eat().value());
            case OpenParenthesis:
                this.eat();
                final Expression value = parseExpression();
                this.expect(TokenType.CloseParenthesis);

                return value;
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

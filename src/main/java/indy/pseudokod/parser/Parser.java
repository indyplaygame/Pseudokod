package indy.pseudokod.parser;

import indy.pseudokod.ast.*;
import indy.pseudokod.exceptions.*;
import indy.pseudokod.lexer.Token;
import indy.pseudokod.lexer.TokenType;
import indy.pseudokod.runtime.values.ValueType;

import java.util.*;

import indy.pseudokod.lexer.Lexer;

/**
 * The {@link Parser} is responsible for analyzing a list of tokens produced by the {@link Lexer}
 * and constructing an Abstract Syntax Tree (AST). It processes the tokens generated
 * by the lexer and applies the rules of the language grammar to produce
 * a structured representation of the source code.
 */
public class Parser {
    private List<Token> tokens;
    private static final Map<String, ValueType> var_types = new HashMap<>();
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
        var_types.put("plate", ValueType.Stack);
        var_types.put("talerz", ValueType.Stack);
        var_types.put("queue", ValueType.Queue);
        var_types.put("kolejka", ValueType.Queue);
    }

    /**
     * Checks if the current token is not the end of the file.
     *
     * @return {@code true} if the current token is not of type {@link TokenType#EndOfFile},
     *         otherwise {@code false}.
     */
    private boolean isNotEOF() {
        return this.tokens.get(0).type() != TokenType.EndOfFile;
    }

    /**
     * Retrieves the current {@link Token} without removing it from the list.
     *
     * @return The current {@link Token} at the front of the list.
     */
    private Token at() {
        return this.tokens.get(0);
    }

    /**
     * Removes and returns the current {@link Token} from the list.
     *
     * @return The {@link Token} that was removed from the front of the list.
     */
    private Token eat() {
        return this.tokens.remove(0);
    }

    /**
     * Skips over tokens that are considered skippable, such as newlines and indentations.
     */
    private void removeSkippable() {
        while(!tokens.isEmpty() && (this.at().type() == TokenType.NewLine || this.at().type() == TokenType.Indent)) {
            this.eat();
        }
    }

    /**
     * Consumes and returns the current {@link Token} if it matches the specified {@link TokenType}.
     *
     * @param type The expected type of the current token.
     * @return The {@link Token} that matches the expected type.
     * @throws MissingTokenException If the {@link Token} is missing or
     * does not match the expected {@link TokenType}.
     */
    private Token expect(TokenType type) throws MissingTokenException {
        if(tokens.isEmpty()) throw new MissingTokenException(type);
        final Token prev = this.eat();

        if(prev != null && prev.type() == type) return prev;
        else {
            assert prev != null;
            throw new MissingTokenException(type, prev.type());
        }
    }

    /**
     * Consumes a specific number of tokens if they match the specified {@link TokenType}.
     *
     * @param type The expected type of the tokens.
     * @param n The number of tokens to consume.
     * @return {@code true} if all tokens match the expected type, {@code false} otherwise.
     */
    private boolean expect(TokenType type, int n) {
        for(int i = 0; i < n; i++) if(!this.tokens.get(i).type().equals(type)) return false;
        for(int i = 0; i < n; i++) this.eat();
        return true;
    }

    /**
     * Consumes the current {@link Token} if it matches any of the specified types.
     *
     * @param types The expected types of the next token.
     * @return The {@link Token} that matches one of the expected types.
     * @throws MissingTokenException If the token does not match any of the expected types.
     */
    private Token expect(TokenType... types) throws MissingTokenException {
        if(Arrays.stream(types).anyMatch(t -> this.at().type().equals(t))) return this.eat();
        else throw new MissingTokenException(this.at().type(), types);
    }

    /**
     * Parses a statement from the current position in the token list and returns its corresponding {@link Statement}.
     * This method identifies the type of statement based on the current token and delegates parsing
     * to the appropriate method.
     *
     * @return A {@link Statement} object representing the parsed statement.
     * @throws Throwable If an error occurs during parsing, such as unexpected tokens or syntax issues.
     */
    private Statement parseStatement() throws Throwable {
        final TokenType type = this.at().type();

        switch(type) {
            case DataToken:
                return this.parseDataDeclarationStatement();
            case Function:
                return this.parseFunctionDeclaration();
            case PrintFunction:
                return this.parsePrintFunction();
            case IfStatement:
                return this.parseIfStatement();
            case ElseStatement:
                return this.parseElseStatement();
            case ForStatement:
                return this.parseForStatement();
            case WhileStatement:
                return this.parseWhileStatement();
            case DoStatement:
                return this.parseDoStatement();
            case ReturnToken:
                // TODO: Fix return statements in the if statement
                this.eat();
                Expression value = this.parseExpression();

                return new ReturnStatement(value);
            case ImportToken:
                this.eat();
                this.expect(TokenType.Apostrophe);
                String path = this.expect(TokenType.Character).value();
                this.expect(TokenType.Apostrophe);

                return new ImportStatement(path);
            default:
                return this.parseExpression();
        }
    }

    /**
     * Parses a data declaration statement from the token stream and returns a {@link DataDeclaration} object.<br><br>
     *
     * The method processes declarations of variables or ranges, ensuring correct syntax for data types, identifiers,
     * optional ranges, and optional assignment values. Multiple variables can be declared in a single statement,
     * separated by commas.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * data:
     *   number x <- 5,
     *   number y in (1, 10),
     *   text name <- "Alice";
     * </pre>
     *
     * @return A {@link DataDeclaration} object containing a list of {@link VariableDeclaration} statements.
     * @throws Throwable If syntax errors are encountered or illegal data types are used with ranges.
     */
    private DataDeclaration parseDataDeclarationStatement() throws Throwable {
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
            Expression range = null;
            Expression value = null;

            if(!tokens.isEmpty() && this.at().type() == TokenType.InRange) {
                if(!(data_type.value().equals("number") || data_type.value().equals("liczba")))
                    throw new IllegalDataTypeException(data_type.value());

                this.eat();

                if(this.at().type().equals(TokenType.Identifier)) range = parseExpression();
                else if(this.at().type().equals(TokenType.OpenBrace)) range = parsePrimaryExpression();
                else {
                    boolean left_included = this.expect(TokenType.OpenParenthesis, TokenType.OpenBracket).type().equals(TokenType.OpenBracket);
                    Expression left = parseExpression();
                    this.expect(TokenType.Comma);
                    Expression right = parseExpression();
                    boolean right_included = this.expect(TokenType.CloseParenthesis, TokenType.CloseBracket).type().equals(TokenType.CloseBracket);

                    range = new RangeLiteral(left, right, left_included, right_included);
                }
            }

            if(!tokens.isEmpty() && this.at().type() == TokenType.Assignment) {
                this.eat();
                value = this.parseExpression();
            }

            if(range == null) statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false, value));
            else statements.add(new VariableDeclaration(var_types.get(data_type.value()), identifier.value(), false, range, value));

        } while(this.at().type() == TokenType.Comma);

        if(this.at().type().equals(TokenType.Semicolon)) this.eat();
        return new DataDeclaration(statements);
    }

    /**
     * Parses a function declaration statement and returns a {@link FunctionDeclaration} object.<br><br>
     *
     * The function declaration includes: function name (identifier), data declarations (parameters)
     * within the function scope, return data type specified after the <code>result</code> keyword,
     * function body comprising a sequence of statements.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * function greet
     *   data:
     *      number x <- 10,
     *      text name*;
     *
     *   result: number;
     *
     *   print "Hello", name;
     *   return x;</pre>
     *
     * @return A {@link FunctionDeclaration} object representing the parsed function.
     * @throws Throwable If syntax errors occur, such as missing tokens or incorrect indentation.
     */
    private FunctionDeclaration parseFunctionDeclaration() throws Throwable {
        this.eat();
        Token identifier = this.expect(TokenType.Identifier);
        List<Statement> data;
        this.expect(TokenType.NewLine);

        indent++;
        if(!this.expect(TokenType.Indent, indent)) throw new IncorrectFunctionDeclarationSyntaxException(this.at().type());

        if(this.at().type().equals(TokenType.DataToken)) data = parseDataDeclarationStatement().body();
        else throw new MissingTokenException(TokenType.DataToken, this.at().type());

        this.expect(TokenType.NewLine);
        while(this.at().type().equals(TokenType.NewLine)) this.eat();
        if(!this.expect(TokenType.Indent, indent)) throw new IncorrectFunctionDeclarationSyntaxException(this.at().type());
        this.expect(TokenType.ResultToken);
        this.expect(TokenType.Colon);
        this.removeSkippable();
        Token data_type = this.expect(TokenType.DataType);
        if(this.at().type().equals(TokenType.Semicolon)) this.eat();

        List<Statement> body = new ArrayList<>();

        while(this.at().type().equals(TokenType.NewLine)) {
            Token token = this.eat();
            if(this.expect(TokenType.Indent, indent)) body.add(this.parseStatement());
            else if(!this.at().type().equals(TokenType.NewLine)) {
                this.tokens.add(0, token);
                break;
            }
        }
        indent--;

        return new FunctionDeclaration(identifier.value(), data, var_types.get(data_type.value()), body);
    }

    /**
     * Parses a print function call from the token stream and returns a {@link PrintFunction} object.<br><br>
     *
     * The print function processes one or more arguments, separated by commas,
     * and constructs a {@link PrintFunction} node for the Abstract Syntax Tree (AST).
     * This method expects at least one expression to be passed as an argument to the print function.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * print "Hello, world";</pre>
     *
     * @return A {@link PrintFunction} object containing the list of parsed expressions as arguments.
     * @throws Throwable If unexpected tokens are encountered, such as a semicolon after the print keyword.
     */
    private PrintFunction parsePrintFunction() throws Throwable {
        this.eat();
        if (this.at().type().equals(TokenType.Semicolon)) throw new UnexpectedTokenException(this.at());

        List<Expression> args = new ArrayList<>();
        args.add(parseExpression());

        while(this.isNotEOF() && this.at().type().equals(TokenType.Comma)) {
            this.expect(TokenType.Comma);
            args.add(parseExpression());
        }

        return new PrintFunction(args);
    }

    /**
     * Parses an if-statement from the token stream and returns an {@link IfStatement} object.<br><br>
     *
     * The method processes the condition, the body of the if-statement, and optionally an
     * attached else-statement. It supports nested statements and ensures proper indentation
     * structure is maintained during parsing.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * if x > 10:
     *     print "x is greater than 10"
     * else:
     *     print "x is not greater than 10"</pre>
     *
     * @return An {@link IfStatement} object representing the parsed if-else statement in the AST.
     * @throws Throwable If any unexpected tokens or indentation issues occur.
     */
    private IfStatement parseIfStatement() throws Throwable {
        indent++;
        this.eat();

        Expression expression = parseExpression();
        List<Statement> body = new ArrayList<>();

        while(this.isNotEOF() && this.at().type().equals(TokenType.NewLine)) {
            this.expect(TokenType.NewLine);
            if (!this.expect(TokenType.Indent, indent)) break;
            body.add(parseStatement());
        }
        indent--;

        if(this.at().type().equals(TokenType.Semicolon)) this.eat();
        this.removeSkippable();

        if(this.isNotEOF() && this.at().type().equals(TokenType.ElseStatement))
            return new IfStatement(expression, body, parseStatement());

        return new IfStatement(expression, body);
    }

    /**
     * Parses an else or elseif-statement from the token stream and returns the corresponding {@link Statement} object.<br><br>
     *
     * This method handles both standalone else-statements and elseif-statements with their conditions.
     * It ensures correct parsing of the body and maintains proper indentation levels during parsing.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * if x < 10:
     *     print "x is less than 10"
     * else if x > 10:
     *     print "x is greater than 10"
     * else:
     *     print "x is equal to 10"</pre>
     *
     * @return A {@link ElseStatement} or {@link IfStatement} object representing the parsed else or elseif construct in the AST.
     * @throws Throwable If unexpected tokens, incorrect indentation, or missing conditions are encountered.
     */
    private Statement parseElseStatement() throws Throwable {
        this.eat();

        Expression expression = null;
        List<Statement> body = new ArrayList<>();
        boolean elseif = this.at().type().equals(TokenType.IfStatement);

        indent++;
        if(elseif) {
            this.eat();
            expression = parseExpression();
        }

        while (this.isNotEOF() && this.at().type().equals(TokenType.NewLine)) {
            this.expect(TokenType.NewLine);
            if (!this.expect(TokenType.Indent, indent)) break;
            body.add(parseStatement());
        }
        indent--;

        if (elseif) return new IfStatement(expression, body);
        else return new ElseStatement(body);
    }

    /**
     * Parses a for-statement from the token stream and returns a {@link ForStatement} object.<br><br>
     *
     * The for-statement is used to iterate over a set of values or a range in the pseudocode.
     * This method handles parsing of the loop variable, values or range, and the body of the loop.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * for i = 1, 2, 3:
     *     print i
     *
     * for i = 1, 2, ..., 10:
     *     print i</pre>
     *
     *
     * @return A {@link ForStatement} object representing the parsed for-loop in the AST.
     * @throws Throwable If unexpected tokens, missing identifiers, or incorrect syntax are encountered.
     */
    private ForStatement parseForStatement() throws Throwable {
        this.eat();

        Identifier identifier = new Identifier(this.expect(TokenType.Identifier).value());
        ArrayList<Expression> values = new ArrayList<>();
        this.expect(TokenType.Equals);

        if (!this.at().type().equals(TokenType.NewLine)) values.add(parseExpression());

        while (this.isNotEOF() && !this.at().type().equals(TokenType.NewLine)) {
            this.expect(TokenType.Comma);

            if (isNotEOF() && this.at().type().equals(TokenType.Dot)) {
                this.eat();
                this.expect(TokenType.Dot);
                this.expect(TokenType.Dot);

                values.add(new EllipsisStatement());
            } else {
                values.add(parseExpression());
            }
        }

        indent++;
        List<Statement> body = new ArrayList<>();

        while(this.isNotEOF() && this.at().type().equals(TokenType.NewLine)) {
            Token token = this.expect(TokenType.NewLine);
            if(this.expect(TokenType.Indent, indent)) body.add(this.parseStatement());
            else {
                this.tokens.add(0, token);
                break;
            }
        }
        indent--;

        return new ForStatement(identifier.symbol(), values, body);
    }

    /**
     * Parses a while-statement from the token stream and returns a {@link WhileStatement} object.<br><br>
     *
     * The while-statement is used for looping as long as the specified condition evaluates to true.
     * This method parses the condition and the loop body, ensuring proper indentation and syntax.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * while x < 10:
     *     print x
     *     x <- x + 1</pre>
     *
     * @return A {@link WhileStatement} object representing the parsed while-loop in the AST.
     * @throws Throwable If unexpected tokens, missing conditions, or incorrect syntax are encountered.
     */
    private WhileStatement parseWhileStatement() throws Throwable {
        this.eat();
        Expression expression = this.parseExpression();
        List<Statement> body = new ArrayList<>();

        indent++;
        while (this.isNotEOF() && this.at().type().equals(TokenType.NewLine)) {
            Token token = this.expect(TokenType.NewLine);
            if (this.expect(TokenType.Indent, indent)) body.add(this.parseStatement());
            else {
                this.tokens.add(0, token);
                break;
            }
        }
        indent--;

        return new WhileStatement(expression, body, false);
    }

    /**
     * Parses a do-while loop from the token stream and returns a {@link WhileStatement} object.<br><br>
     *
     * The do-while loop ensures that the loop body executes at least once before
     * the condition is checked. This method parses the loop body, condition, and manages
     * proper indentation to determine the scope.<br><br>
     *
     * Example pseudocode handled by this method:
     * <pre>
     * do:
     *     print x
     *     x <- x + 1
     * while x < 10</pre>
     *
     * @return A {@link WhileStatement} object representing the parsed do-while loop in the AST.
     * @throws Throwable If unexpected tokens, missing conditions, or incorrect syntax are encountered.
     */
    private WhileStatement parseDoStatement() throws Throwable {
        this.eat();
        List<Statement> body = new ArrayList<>();

        indent++;
        while(this.isNotEOF() && !this.at().type().equals(TokenType.WhileStatement)) {
            Token token = this.expect(TokenType.NewLine);
            if(this.expect(TokenType.Indent, indent)) body.add(this.parseStatement());
            else {
                this.tokens.add(0, token);
                break;
            }
        }
        indent--;

        this.expect(TokenType.NewLine);
        this.expect(TokenType.WhileStatement);
        Expression expression = this.parseExpression();

        return new WhileStatement(expression, body, true);
    }

    /**
     * Parses an expression, ensuring the correct order of operations, starting from the operation with lowest priority.
     * This method is the entry point for parsing expressions and delegates the parsing
     * to the {@link #parseAssignmentExpression()} method to handle assignments and other expressions.
     *
     * @return The parsed {@link Expression}.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseExpression() throws Throwable {
        return parseAssignmentExpression();
    }

    /**
     * Parses an assignment expression, which can assign a value to a variable.
     * This method first parses a {@link LogicalExpression} using the {@link #parseLogicalExpression()} method,
     * then checks if the current token is anassignment operator. If so, it recursively parses the right-hand side expression and creates
     * an {@link AssignmentExpression} node, otherwise returns the parsed {@link LogicalExpression}.
     *
     * @return The parsed {@link Expression}, which could be an {@link AssignmentExpression} or a {@link LogicalExpression}.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseAssignmentExpression() throws Throwable {
        Expression left = parseLogicalExpression();

        if(this.at().type() == TokenType.Assignment) {
            this.eat();

            final Expression value = this.parseAssignmentExpression();

            return new AssignmentExpression(left, value);
        }

        return left;
    }

    /**
     * Parses a logical expression, which can include logical operators like AND, OR, NOT or XOR.
     * This method first parses a {@link BitwiseExpression} using the {@link #parseBitwiseExpression()} method,
     * then checks for logical operators in the token stream. If any are found, it parses additional
     * bitwise expressions and combines them using logical operations.
     *
     * @return The parsed {@link Expression}, which may be a chain of {@link LogicalExpression} objects.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseLogicalExpression() throws Throwable {
        Expression left = parseBitwiseExpression();

        while(this.at().type().equals(TokenType.LogicalOperator)) {
            String operator = this.eat().value();
            Expression right = parseBitwiseExpression();
            left = new LogicalExpression(left, right, operator);
        }

        return left;
    }

    /**
     * Parses a bitwise expression, which can include bitwise operators like AND, OR, XOR or NOT.
     * This method first parses a {@link ComparisonExpression} using the {@link #parseComparisonExpression()} method,
     * then checks for bitwise operators in the token stream. If any are found, it recursively parses additional comparison
     * expressions and combines them using bitwise operations.
     *
     * @return The parsed {@link Expression}, which may be a chain of {@link BitwiseExpression} objects.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseBitwiseExpression() throws Throwable {
        Expression left = parseComparisonExpression();

        while(this.at().type().equals(TokenType.BitwiseOperator)) {
            String operator = this.eat().value();
            Expression right = parseComparisonExpression();
            left = new BitwiseExpression(left, right, operator);
        }

        return left;
    }

    /**
     * Parses a comparison expression, which can include comparison operators like ">", "<", "==", "!=", ">=" or "<=".
     * This method first parses a shift expression using the {@link #parseShiftExpression()} method,
     * then checks for comparison operators or equality operators in the token stream.
     * If any are found, it recursively parses additional shift expressions and combines them using comparison operations.
     *
     * @return The parsed {@link Expression}, which may be a chain of {@link ComparisonExpression} objects.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseComparisonExpression() throws Throwable {
        Expression left = parseShiftExpression();

        while(this.at().type().equals(TokenType.ComparisonOperator) || this.at().type().equals(TokenType.Equals)) {
            String operator = this.eat().value();
            Expression right = parseShiftExpression();
            left = new ComparisonExpression(left, right, operator);
        }

        return left;
    }

    /**
     * Parses a shift expression, which can include left or right shift operators.
     * This method first parses an additive expression, represented by {@link BinaryExpression}, using the {@link #parseAdditiveExpression()} method,
     * then checks for shift operators (<< or >>) in the token stream. If a shift operator is found, it recursively parses additional additive
     * expressions and combines them using bitwise shift operations.
     *
     * @return The parsed {@link Expression}, which may be a chain of {@link BitwiseExpression} objects representing the shift operations.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseShiftExpression() throws Throwable {
        Expression left = parseAdditiveExpression();

        while(this.at().type().equals(TokenType.ShiftOperator)) {
            String operator = this.eat().value();
            Expression right = parseAdditiveExpression();
            left = new BitwiseExpression(left, right, operator);
        }

        return left;
    }

    /**
     * Parses an additive expression, which can include addition (+) and subtraction (-) operators.
     * This method first parses a multiplicative expression, represented by {@link BinaryExpression}, using the {@link #parseMultiplicativeExpression()} method,
     * then checks for addition or subtraction operators in the token stream. If such operators are found, it recursively parses additional multiplicative expressions
     * and combines them using binary operations.
     *
     * @return The parsed {@link Expression}, which may be a chain of {@link BinaryExpression} objects representing
     *         addition or subtraction operations.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseAdditiveExpression() throws Throwable {
        Expression left = parseMultiplicativeExpression();

        while(this.at().value().equals("+") || this.at().value().equals("-")) {
            String operator = this.eat().value();
            Expression right = parseMultiplicativeExpression();
            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    /**
     * Parses a multiplicative expression, which can include multiplication (*), division (/),
     * modulus (mod), and integer division (div) operators.
     * This method first parses a {@link CallExpression} or a {@link IndexExpression} using the {@link #parseCallIndexExpression()},
     * then checks for any multiplicative operators in the token stream. If one is found, it recursively parses the next operand and constructs a
     * {@link BinaryExpression} representing the multiplication, division, modulus, or integer division operation.
     *
     * @return The parsed {@link Expression}, which may be a chain of {@link BinaryExpression} objects representing
     *         multiplicative operations such as multiplication, division, modulus, and integer division.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseMultiplicativeExpression() throws Throwable {
        Expression left = parseCallIndexExpression();

        while(this.at().value().equals("*") || this.at().value().equals("/") || this.at().value().equals("mod") || this.at().value().equals("div")) {
            String operator = this.eat().value();
            Expression right = parseCallIndexExpression();
            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    /**
     * Parses an expression that may be an index expression or a function call.
     * This method first parses an {@link IndexExpression} using the {@link #parseIndexExpression()} method.
     * If the next token indicates the start of a function call (an opening parenthesis "("), it proceeds to parse the
     * function call expression. Otherwise, it returns the parsed {@link IndexExpression}.
     *
     * @return The parsed {@link Expression}, which can either be an {@link IndexExpression} or a
     *         {@link CallExpression} depending on whether the next token represents a function call.
     * @throws Throwable If an error occurs during parsing.
     */
    private Expression parseCallIndexExpression() throws Throwable {
        Expression expression = parseIndexExpression();

        if(this.at().type().equals(TokenType.OpenParenthesis))
            return parseCallExpression(expression);

        return expression;
    }

    /**
     * Parses an {@link IndexExpression}, which involves an array or collection access using square brackets.
     * This method first parses a primary expression, which is typically a variable or an array, using the {@link #parsePrimaryExpression()} method.
     * Then, it repeatedly checks for index access (square brackets) and parses the index expression inside the brackets.
     * The method ensures that the index is of a valid type (numeric literal, identifier, or binary expression).
     *
     * @return The parsed {@link Expression}, which represents an indexed array or collection.
     * @throws Throwable If an error occurs during parsing, such as encountering an invalid index type.
     */
    private Expression parseIndexExpression() throws Throwable {
        Expression array = parsePrimaryExpression();

        while(this.at().type().equals(TokenType.OpenBracket)) {
            this.eat();

            final Expression index = parseExpression();
            if(!(index.kind().equals(NodeType.NumericLiteral) || index.kind().equals(NodeType.Identifier) || index.kind().equals(NodeType.BinaryExpression)))
                throw new IllegalIndexTypeException(index.kind());
            this.expect(TokenType.CloseBracket);

            array = new IndexExpression(array, index);
        }
        return array;
    }

    /**
     * Parses a function call expression.
     * This method parses the function call by first parsing the expression representing the function
     * being called, followed by parsing the arguments enclosed in parentheses.
     *
     * @param expression The {@link Expression} representing the function being called.
     * @return A {@link CallExpression} representing the function call with the parsed arguments.
     * @throws Throwable If an error occurs while parsing the function call or arguments.
     */
    private Expression parseCallExpression(Expression expression) throws Throwable {
        return new CallExpression(expression, parseArguments());
    }

    /**
     * Parses the arguments of a function or method call.
     * This method expects the arguments to be enclosed in parentheses. It calls {@link #parseArgumentsList}
     * to parse the list of arguments if there are any, or returns an empty list if no arguments are present.
     *
     * @return A list of {@link Expression} objects representing the arguments of the function call.
     * @throws Throwable If an error occurs while parsing the arguments.
     */
    private List<Expression> parseArguments() throws Throwable {
        this.expect(TokenType.OpenParenthesis);
        final List<Expression> args = this.at().type().equals(TokenType.CloseParenthesis) ? new ArrayList<>() : this.parseArgumentsList();
        this.expect(TokenType.CloseParenthesis);

        return args;
    }

    /**
     * Parses a list of arguments for a function or method call.
     * This method parses the list of arguments separated by commas inside the parentheses of the function call.
     *
     * @return A list of {@link Expression} objects representing the arguments of the function call.
     * @throws Throwable If an error occurs while parsing the arguments.
     */
    private List<Expression> parseArgumentsList() throws Throwable {
        final List<Expression> args = new ArrayList<>();
        args.add(parseExpression());

        while(this.isNotEOF() && this.at().type().equals(TokenType.Comma)) {
            this.eat();
            args.add(parseExpression());
        }

        return args;
    }

    /**
     * Parses the primary expression from the token stream.
     * A primary expression is the simplest form of an expression and may include:
     * identifiers, literals (numbers, strings, characters), parenthesized
     * expressions, and constructs like array or set literals.
     *
     * @return An {@link Expression} representing the parsed primary construct. The return type
     *         can vary based on the token, including:
     *         <ul>
     *         <li>{@link Identifier}</li>
     *         <li>{@link NumericLiteral}</li>
     *         <li>{@link StringLiteral}</li>
     *         <li>{@link CharacterLiteral}</li>
     *         <li>{@link ArrayLiteral}</li>
     *         <li>{@link SetLiteral}</li>
     *         <li>{@link BinaryExpression}</li>
     *         <li>{@link LogicalExpression}</li>
     *         <li>{@link BitwiseExpression}</li>
     *         <li>{@link GetFunction}</li>
     *         </ul>
     * @throws CharactersAmountException If a character literal contains more than one character.
     * @throws IllegalExpressionStartException If an invalid operator or keyword is encountered at
     *                                         the start of the expression.
     * @throws UnexpectedTokenException If the current token does not match the expected types
     *                                  for a primary expression.
     */
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

                        values.add(new EllipsisStatement());
                    } else {
                        values.add(parseExpression());
                    }
                }

                this.expect(TokenType.CloseBrace);
                return new SetLiteral(values);
            case OpenParenthesis: {
                this.eat();
                final Expression value = parseExpression();
                this.expect(TokenType.CloseParenthesis);

                return value;
            } case BinaryOperator: {
                String operator = this.eat().value();

                return new BinaryExpression(new NumericLiteral("0"), parseExpression(), operator);
            } case LogicalOperator: {
                String operator = this.eat().value();

                if (!(operator.equals("NOT") || operator.equals("NIE") || operator.equals("¬")))
                    throw new IllegalExpressionStartException(ValueType.Boolean);

                return new LogicalExpression(parseExpression(), operator);
            } case BitwiseOperator:
                String operator = this.eat().value();

                if(!operator.equals("~")) throw new IllegalExpressionStartException(ValueType.Boolean);

                return new BitwiseExpression(parseExpression(), operator);
            case GetFunction:
                this.eat();
                String identifier = this.expect(TokenType.Identifier).value();

                return new GetFunction(identifier);
            default:
                throw new UnexpectedTokenException(this.at());
        }
    }

    /**
     * Produces the Abstract Syntax Tree (AST) for the given source code.
     * This method tokenizes the input source code using the {@link Lexer#tokenize(String)} method, parses the tokens into a series
     * of statements, and organizes them into a program structure. The resulting
     * AST represents the hierarchical structure of the program.
     *
     * @param source The source code to be parsed into an AST.
     * @return A {@link Program} object containing the parsed statements
     *         that represent the program's body.
     * @throws Throwable If any error occurs during tokenization or parsing,
     *                   such as syntax errors or unexpected tokens.
     */
    public Program produceAST(String source) throws Throwable {
        this.tokens = Lexer.tokenize(source);
        final List<Statement> program_body = new ArrayList<>();

        while(isNotEOF()) {
            this.removeSkippable();
            program_body.add(this.parseStatement());
            while(this.at().type().equals(TokenType.Semicolon)) this.eat();
        }

        return new Program(program_body);
    }
}

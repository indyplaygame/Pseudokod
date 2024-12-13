package indy.pseudokod.ast;

import indy.pseudokod.exceptions.IllegalDataTypeException;
import indy.pseudokod.runtime.values.ValueType;

/**
 * Represents a variable declaration statement in the abstract syntax tree (AST).
 * A variable declaration introduces a variable with a specified name, type and optional value.
 * It also supports declaring constants and specifying numeric ranges for number types.
 */
public class VariableDeclaration extends Statement {
    private final boolean constant;
    private final String symbol;
    private final ValueType type;
    private final Expression range;
    private final Expression value;

    /**
     * Constructs a new instance of {@link VariableDeclaration}.
     *
     * @param type The type of the variable being declared represented by {@link ValueType}.
     * @param symbol The name of the variable.
     * @param constant A flag indicating if the variable is a constant.
     * @param value The initial value assigned to the variable.
     */
    public VariableDeclaration(ValueType type, String symbol, boolean constant, Expression value) {
        super(NodeType.VariableDeclaration);
        this.type = type;
        this.symbol = symbol;
        this.constant = constant;
        this.value = value;
        this.range = null;
    }

    /**
     * Constructs a new instance of {@link VariableDeclaration} with a numeric range.
     * This constructor validates that the variable type is {@link ValueType#Number}.
     *
     * @param type The type of the variable (must be {@link ValueType#Number} if range is provided).
     * @param symbol The name of the variable.
     * @param constant A flag indicating if the variable is a constant.
     * @param range The range expression specifying the numeric range of the variable.
     * @param value The initial value assigned to the variable.
     * @throws IllegalDataTypeException If the variable type is not {@link ValueType#Number}.
     */
    public VariableDeclaration(ValueType type, String symbol, boolean constant, Expression range, Expression value) throws IllegalDataTypeException {
        super(NodeType.VariableDeclaration);
        if(type.equals(ValueType.Number)) this.range = range;
        else throw new IllegalDataTypeException(type);
        this.type = type;
        this.symbol = symbol;
        this.constant = constant;
        this.value = value;
    }

    /**
     * @return {@code true} if the variable is a constant, {@code false} otherwise.
     */
    public boolean constant() {
        return this.constant;
    }

    /**
     * @return The name of the variable.
     */
    public String symbol() {
        return this.symbol;
    }

    /**
     * @return The initial value assigned to the variable.
     */
    public Expression value() {
        return this.value;
    }

    /**
     * @return The type of the variable represented by {@link ValueType}.
     */
    public ValueType type() {
        return this.type;
    }

    /**
     * @return The numeric range expression if the variable type is {@link ValueType#Number}, otherwise {@code null}.
     */
    public Expression range() {
        return this.range;
    }
}

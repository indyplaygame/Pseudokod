package indy.pseudokod.ast;

import indy.pseudokod.exceptions.IllegalDataTypeException;
import indy.pseudokod.runtime.values.NumberValue;
import indy.pseudokod.runtime.values.ValueType;

public class VariableDeclaration extends Statement {
    private final boolean constant;
    private final String symbol;
    private final ValueType type;
    private RangeLiteral range = null;
    private Expression value;

    public VariableDeclaration(ValueType type, String symbol, boolean constant, Expression value) {
        super(NodeType.VariableDeclaration);
        this.type = type;
        this.symbol = symbol;
        this.constant = constant;
        this.value = value;
    }

    public VariableDeclaration(ValueType type, String symbol, boolean constant) {
        super(NodeType.VariableDeclaration);
        this.type = type;
        this.symbol = symbol;
        this.constant = constant;
    }

    public VariableDeclaration(ValueType type, String symbol, boolean constant, RangeLiteral range) throws IllegalDataTypeException {
        super(NodeType.VariableDeclaration);
        this.type = type;
        this.symbol = symbol;
        this.constant = constant;
        if(type.equals(ValueType.Number)) this.range = range;
        else throw new IllegalDataTypeException(type.name());
    }

    public VariableDeclaration(ValueType type, String symbol, boolean constant, RangeLiteral range, Expression value) throws IllegalDataTypeException {
        super(NodeType.VariableDeclaration);
        this.type = type;
        this.symbol = symbol;
        this.constant = constant;
        this.value = value;
        if(type.equals(ValueType.Number)) this.range = range;
        else throw new IllegalDataTypeException(type.name());
    }

    public boolean constant() {
        return this.constant;
    }

    public String symbol() {
        return this.symbol;
    }

    public Expression value() {
        return this.value;
    }

    public ValueType type() {
        return this.type;
    }

    public RangeLiteral range() {
        return this.range;
    }
}

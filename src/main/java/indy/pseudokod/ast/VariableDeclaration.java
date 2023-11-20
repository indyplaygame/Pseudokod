package indy.pseudokod.ast;

import indy.pseudokod.runtime.values.ValueType;

public class VariableDeclaration extends Statement {
    private boolean constant;
    private String symbol;
    private ValueType type;
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
}

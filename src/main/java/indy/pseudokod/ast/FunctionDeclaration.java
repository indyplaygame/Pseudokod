package indy.pseudokod.ast;

import indy.pseudokod.runtime.values.ValueType;

import java.util.List;

public class FunctionDeclaration extends Statement {

    private final String symbol;
    private final List<Statement> data;
    private final ValueType result;
    private final List<Statement> body;

    public FunctionDeclaration(String symbol, List<Statement> data, ValueType result, List<Statement> body, Expression return_value) {
        super(NodeType.FunctionDeclaration);
        this.symbol = symbol;
        this.data = data;
        this.result = result;
        this.body = body;
    }

    public String symbol() {
        return this.symbol;
    }

    public List<Statement> data() {
        return this.data;
    }

    public ValueType result() {
        return this.result;
    }

    public List<Statement> body() {
        return this.body;
    }
}

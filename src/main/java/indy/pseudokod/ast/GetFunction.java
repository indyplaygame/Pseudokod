package indy.pseudokod.ast;

import java.util.List;

public class GetFunction extends Expression {

    private final String identifier;

    public GetFunction(String identifier) {
        super(NodeType.GetFunction);
        this.identifier = identifier;
    }

    public String identifier() {
        return this.identifier;
    }
}

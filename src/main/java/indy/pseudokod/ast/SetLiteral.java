package indy.pseudokod.ast;

import java.util.ArrayList;

public class SetLiteral extends Expression {
    ArrayList<Expression> values;

    public SetLiteral(ArrayList<Expression> values) {
        super(NodeType.SetLiteral);
        this.values = values;
    }

    public ArrayList<Expression> values() {
        return this.values;
    }
}

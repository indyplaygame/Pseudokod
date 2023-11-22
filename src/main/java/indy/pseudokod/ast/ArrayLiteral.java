package indy.pseudokod.ast;

import java.util.ArrayList;

public class ArrayLiteral extends Expression {
    ArrayList<Expression> values;

    public ArrayLiteral(ArrayList<Expression> values) {
        super(NodeType.ArrayLiteral);
        this.values = values;
    }

    public ArrayList<Expression> values() {
        return this.values;
    }
}

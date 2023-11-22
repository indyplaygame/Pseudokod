package indy.pseudokod.exceptions;

import indy.pseudokod.ast.NodeType;

public class IllegalIndexTypeException extends Exception {
    public IllegalIndexTypeException(NodeType type) {
        super("Identifier or NumericLiteral expected as list index, received '" + type.name() + "'.");
    }
}

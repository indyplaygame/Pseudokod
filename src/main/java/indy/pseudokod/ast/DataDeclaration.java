package indy.pseudokod.ast;

import java.util.List;

public class DataDeclaration extends Statement {
    List<Statement> variables;

    public DataDeclaration(List<Statement> statements) {
        super(NodeType.DataDeclaration);
        this.variables = statements;
    }

    public List<Statement> body() {
        return this.variables;
    }
}

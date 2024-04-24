package indy.pseudokod.ast;

public class ImportStatement extends Statement {
    String path;

    public ImportStatement(String path) {
        super(NodeType.ImportStatement);
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}

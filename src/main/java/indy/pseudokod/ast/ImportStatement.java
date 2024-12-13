package indy.pseudokod.ast;

/**
 * Represents an import statement in the abstract syntax tree (AST).
 */
public class ImportStatement extends Statement {
    private final String path;

    /**
     * Constructs a new instance of {@link ImportStatement} with the given import path.
     *
     * @param path The path to a file to be imported.
     */
    public ImportStatement(String path) {
        super(NodeType.ImportStatement);
        this.path = path;
    }

    /**
     * @return The import path of {@link ImportStatement}.
     */
    public String path() {
        return this.path;
    }
}

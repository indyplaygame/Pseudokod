package indy.pseudokod.ast;

/**
 * Represents a bitwise expression in the abstract syntax tree (AST).
 * A bitwise expression consists of two expressions and a bitwise operator.
 */
public class BitwiseExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    /**
     * Constructs a new instance of {@link BitwiseExpression} with two operands.
     *
     * @param left The left operand of the bitwise expression.
     * @param right The right operand of the bitwise expression.
     * @param operator The bitwise operator (&, |, ^, <<, >>).
     */
    public BitwiseExpression(Expression left, Expression right, String operator) {
        super(NodeType.BitwiseExpression);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * Constructs a new instance of {@link BitwiseExpression} with a single operand.
     *
     * @param expression The operand of the bitwise expression.
     * @param operator The bitwise operator (~).
     */
    public BitwiseExpression(Expression expression, String operator) {
        super(NodeType.BitwiseExpression);
        this.left = null;
        this.right = expression;
        this.operator = operator;
    }

    /**
     * @return The left operand of the {@link BitwiseExpression}.
     */
    public Expression left() {
        return this.left;
    }

    /**
     * @return The right operand of the {@link BitwiseExpression}.
     */
    public Expression right() {
        return this.right;
    }

    /**
     * @return The bitwise operator of the {@link BitwiseExpression}.
     */
    public String operator() {
        return this.operator;
    }
}

package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.tree.expr.Expression;

/**
 * Represents a statement such as "if expr statement else statement.
 * e.g. "if i < 0 return;", "if (i < 0) { return; } else { }".
 */
public class If extends Statement {

    public Expression condition;
    public Statement thenPart;
    public Statement elsePart;

    public If(Expression condition, Statement thenPart, Statement elsePart, Range range) {
        super(range);
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, condition);
            acceptChild(visitor, thenPart);
            acceptChild(visitor, elsePart);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}

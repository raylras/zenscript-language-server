package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.tree.expr.Expression;

/**
 * Represents a statement such as "while (expr) { statement, ... }".
 * e.g. "while (true) { break; }".
 */
public class While extends Statement {

    public Expression condition;
    public Statement statement;
    public LocalScope localScope;

    public While(Expression condition, Statement statement, Range range) {
        super(range);
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, condition);
            acceptChild(visitor, statement);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}

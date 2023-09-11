package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.symbol.Symbol.Modifier;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.CSTNodes;

import java.util.Objects;

public final class ModifierResolver {

    private ModifierResolver() {}

    public static Modifier getModifier(ParseTree cst) {
        Objects.requireNonNull(cst);
        return cst.accept(DeclaratorVisitor.INSTANCE);
    }

    private static final class DeclaratorVisitor extends Visitor<Modifier> {
        private static final DeclaratorVisitor INSTANCE = new DeclaratorVisitor();

        @Override
        public Modifier visitFormalParameter(FormalParameterContext ctx) {
            return Symbol.Modifier.NONE;
        }

        @Override
        public Modifier visitVariableDeclaration(VariableDeclarationContext ctx) {
            return switch (CSTNodes.getTokenType(ctx.prefix)) {
                case ZenScriptLexer.VAR -> Modifier.VAR;
                case ZenScriptLexer.VAL -> Modifier.VAL;
                case ZenScriptLexer.STATIC -> Modifier.STATIC;
                case ZenScriptLexer.GLOBAL -> Modifier.GLOBAL;
                default -> Modifier.NONE;
            };
        }

        @Override
        public Modifier visitForeachVariable(ForeachVariableContext ctx) {
            return Symbol.Modifier.NONE;
        }

        @Override
        public Modifier visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return switch (CSTNodes.getTokenType(ctx.prefix)) {
                case ZenScriptLexer.STATIC -> Modifier.STATIC;
                case ZenScriptLexer.GLOBAL -> Modifier.GLOBAL;
                default -> Modifier.NONE;
            };
        }

        @Override
        public Modifier visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            return Symbol.Modifier.EXPAND;
        }
    }

}

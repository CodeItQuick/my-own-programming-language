package org.languaging;

import static org.languaging.TokenType.*;

public class SubExpressionLiteralEquality implements Consumable {
    private final Token[] tokens;
    private int current = 0;
    private Expr.Literal expr = null;

    public SubExpressionLiteralEquality(Token... tokens) {
        this.tokens = tokens;
    }

    public Expr process() {
        return consumeEqualityLiteral();
    }

    public Expr.Literal consumeEqualityLiteral() {

        if (check(FALSE)) {
            consume();
            return expr;
        }
        if (check(TRUE)) {
            consume();
            return expr;
        }
        if (check(NIL)) {
            consume();
            return expr;
        }
        return null;
    }
    public void consume() {
        if (match(FALSE)) expr = new Expr.Literal(false);
        if (match(TRUE)) expr = new Expr.Literal(true);
        if (match(NIL)) expr = new Expr.Literal(null);

    }
    public boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                return true;
            }
        }
        return false;
    }

    public boolean check(TokenType... type) {
        boolean matches = false;
        for (TokenType tokenType: type) {
            if (tokenType == null) return false;
            for (Token token: tokens) {
                if (tokenType == token.type) matches = true;
            }
        }
        return matches;
    }

}

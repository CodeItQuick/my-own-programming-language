package org.languaging;

import static org.languaging.TokenType.*;

public class SubExpressionLiteralNumberString implements Consumable {
    private final Token[] tokens;
    private int current = 0;
    private Expr.Literal expr = null;

    public SubExpressionLiteralNumberString(Token... tokens) {
        this.tokens = tokens;
    }

    public Expr process() {
        return consumeNumberLiteral();
    }

    public Expr.Literal consumeNumberLiteral() {
        if(check(NUMBER, STRING)) {
            consume(tokens[tokens.length - 1].literal);
            return expr;
        }
        return null;
    }

    public void consume(Object literal) {
        if (match(NUMBER, STRING)) {
            expr = new Expr.Literal(literal);
        }
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

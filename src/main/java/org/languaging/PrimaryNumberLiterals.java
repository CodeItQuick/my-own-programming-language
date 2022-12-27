package org.languaging;

import java.util.List;

import static org.languaging.TokenType.*;

public class PrimaryNumberLiterals implements Consumable {
    private final List<Token> tokens;
    private int current = 0;
    private Expr.Literal expr = null;

    public PrimaryNumberLiterals(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr process() {
        return consumeNumberLiteral();
    }

    public Expr.Literal consumeNumberLiteral() {
        if(check(NUMBER, STRING)) {
            consume(tokens.get(tokens.size() - 1).literal);
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
            if (tokenType == tokens.get(current).type) matches = true;
        }
        return matches;
    }

}

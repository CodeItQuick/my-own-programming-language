package org.languaging;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.languaging.TokenType.*;

public class SubExpressionLiteralParenthesis implements Consumable {
    private int current = 0;
    private Expr.Literal expr = null;
    private List<Token> tokens;


    public SubExpressionLiteralParenthesis(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr process() {
        return consumeParenthesis();
    }

    public Expr consumeParenthesis() {
        if(check(LEFT_PAREN)) {
            int start = tokens
                    .stream().takeWhile(x -> x.type != LEFT_PAREN)
                    .collect(Collectors.toList()).size() + 1;
            List<Token> beforeRightParen = tokens.stream()
                    .skip(start > 0 ? start : 0)
                    .takeWhile(x -> x.type != RIGHT_PAREN)
                    .collect(Collectors.toList());
            boolean isRightParenPresent = beforeRightParen.size() == tokens.size();
            if (isRightParenPresent) {
                return null;
            }
            Parser parser1 = new Parser(beforeRightParen);
            Expr expr1 = parser1.parse();
            return new Expr.Grouping(expr1);
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

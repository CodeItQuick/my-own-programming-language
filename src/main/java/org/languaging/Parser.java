package org.languaging;

import java.util.List;
import java.util.stream.Collectors;

import static org.languaging.TokenType.*;

public class Parser {
    private static class ParseError extends RuntimeException {

    }
    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Expr parse() {
        try {
            return expression();
        } catch (ParseError error) {
            System.out.println(error.getMessage());
            return null;
        }
//         }
    }

    public Expr expression() {
        return equality();
    }

    public Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    public Expr comparison() {
        Expr expr = term();

        boolean isMatched = false;
        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    public Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    public Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }
    private Expr primary() {
        List<Token> groupingTokens = tokens.stream()
                .skip(current)
                .collect(Collectors.toList());
        List<Consumable> subExpressions = List.of(
                new SubExpressionLiteralEquality(
                List.of(tokens.get(current))),
                new SubExpressionLiteralNumberString(
                        List.of(tokens.get(current))),
                new SubExpressionLiteralParenthesis(
                        groupingTokens
                )
        );
        SubExpressionLiteralProcessing subExpressionLiteralProcessing =
                new SubExpressionLiteralProcessing(subExpressions);
        Expr literal = subExpressionLiteralProcessing.process();
        if (literal != null) {
            if (current < tokens.size() - 1) {
                advance();
            }
            return literal;
        }

        throw error(peek(), "Expect expression.");

    }

    public Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }


    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            advance();
        }
    }

}


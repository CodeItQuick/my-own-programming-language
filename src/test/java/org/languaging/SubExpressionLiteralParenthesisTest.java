package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.languaging.TokenType.*;

class SubExpressionLiteralParenthesisTest {
    @Test
    public void GivenOnlyParenthesisCanParseTerms() {
        List<Token> tokens = List.of(
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1)
        );

        SubExpressionLiteralParenthesis expression =
                new SubExpressionLiteralParenthesis(tokens);

        Expr expressionParsed = expression.process();

        assertThat(expressionParsed).isInstanceOf(Expr.Grouping.class);

    }
    @Test
    public void GivenParenthesisCanParseTerms() {
        List<Token> tokens = List.of(
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1),
                new Token(SEMICOLON, null, ";", 1)
        );

        SubExpressionLiteralParenthesis expression = new SubExpressionLiteralParenthesis(tokens);

        Expr expressionParsed = expression.process();

        assertThat(expressionParsed).isInstanceOf(Expr.Grouping.class);

    }
    @Test
    public void GivenParenthesisWithLeftDataCanParseTerms() {
        List<Token> tokens = List.of(
                new Token(NUMBER, null, "1", 1),
                new Token(PLUS, null, "+", 1),
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1),
                new Token(SEMICOLON, null, ";", 1)
        );

        SubExpressionLiteralParenthesis expression = new SubExpressionLiteralParenthesis(tokens);

        Expr expressionParsed = expression.process();

        assertThat(expressionParsed).isInstanceOf(Expr.Grouping.class);

    }
}
package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.languaging.TokenType.*;

class PrimaryParenthesisLiteralsTest {
    @Test
    public void GivenOnlyParenthesisCanParseTerms() {
        List<Token> tokens = List.of(
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1)
        );

        PrimaryParenthesisLiterals expression =
                new PrimaryParenthesisLiterals(tokens);

        Expr expressionParsed = expression.process();

        assertThat(expressionParsed).isInstanceOf(Expr.Grouping.class);
        String result = new AstPrinter().print(expressionParsed);
        assertThat(result).isEqualTo("(group 1)");

    }
    @Test
    public void GivenParenthesisCanParseTerms() {
        List<Token> tokens = List.of(
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1),
                new Token(SEMICOLON, null, ";", 1)
        );

        PrimaryParenthesisLiterals expression = new PrimaryParenthesisLiterals(tokens);

        Expr expressionParsed = expression.process();

        assertThat(expressionParsed).isInstanceOf(Expr.Grouping.class);
        String result = new AstPrinter().print(expressionParsed);
        assertThat(result).isEqualTo("(group 1)");

    }
    @Test
    public void GivenParenthesisWithLeftDataCanParseTerms() {
        List<Token> tokens = List.of(
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(PLUS, "+", null, 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1)
        );

        PrimaryParenthesisLiterals expression = new PrimaryParenthesisLiterals(tokens);

        Expr expressionParsed = expression.process();

        assertThat(expressionParsed).isInstanceOf(Expr.Grouping.class);
        String result = new AstPrinter().print(expressionParsed);
        assertThat(result).isEqualTo("(group (+ 1 1))");
    }
    @Test
    public void GivenParenthesisRightGroupingParsesTerms() {
        List<Token> tokens = List.of(
                new Token(NUMBER,null,"3",1),
                new Token(STAR,"*",null,1),
                new Token(LEFT_PAREN,null,"(",1),
                new Token(NUMBER,null,"2",1),
                new Token(PLUS,null,"+",1),
                new Token(NUMBER,null,"6",1),
                new Token(RIGHT_PAREN,null,")",1),
                new Token(SEMICOLON,null,";",1),
                new Token(EOF,null,"EOF",1)
        );

        PrimaryParenthesisLiterals expression = new PrimaryParenthesisLiterals(tokens);

        Expr expressionParsed = expression.process();

        assertThat(expressionParsed).isInstanceOf(Expr.Grouping.class);
        String result = new AstPrinter().print(expressionParsed);
        assertThat(result).isEqualTo("(* 3 (+ 2 6))");
    }
}
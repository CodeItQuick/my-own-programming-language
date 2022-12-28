package org.languaging;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.languaging.TokenType.*;

public class PrimaryFactorOperatorTests {

    @Test
    public void givenMultiplicationCanParseResult() {
        List<Token> tokens = List.of(
                new Token(NUMBER, "2", "2", 1),
                new Token(STAR, "*", null, 1),
                new Token(NUMBER, "3", "3", 1)
        );
        PrimaryFactorOperator processor =
                new PrimaryFactorOperator(tokens, 1);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(* 2 3)");
    }
    @Test
    public void givenMultipleMultiplicationCanParseResult() {
        List<Token> tokens = List.of(
                new Token(NUMBER, "2", "2", 1),
                new Token(STAR, "*", null, 1),
                new Token(NUMBER, "3", "3", 1),
                new Token(STAR, "*", null, 1),
                new Token(NUMBER, "4", "4", 1)
        );
        PrimaryFactorOperator processor =
                new PrimaryFactorOperator(tokens, 1);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(* (* 2 3) 4)");
    }
    @Test
    public void givenDivisionCanParseResult() {
        List<Token> tokens = List.of(
                new Token(NUMBER, "2", "2", 1),
                new Token(STAR, "/", null, 1),
                new Token(NUMBER, "3", "3", 1)
        );
        PrimaryFactorOperator processor =
                new PrimaryFactorOperator(tokens, 1);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(/ 2 3)");
    }
    @Test
    public void givenMultipleDivisionCanParseResult() {
        List<Token> tokens = List.of(
                new Token(NUMBER, "2", "2", 1),
                new Token(SLASH, "/", null, 1),
                new Token(NUMBER, "3", "3", 1),
                new Token(SLASH, "/", null, 1),
                new Token(NUMBER, "4", "4", 1)
        );
        PrimaryFactorOperator processor =
                new PrimaryFactorOperator(tokens, 1);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(/ (/ 2 3) 4)");
    }
    // TODO: make the division/multiplication recursive
    @Disabled
    @Test
    public void givenTripleDivisionCanParseResult() {
        List<Token> tokens = List.of(
                new Token(NUMBER, "2", "2", 1),
                new Token(SLASH, "/", null, 1),
                new Token(NUMBER, "3", "3", 1),
                new Token(SLASH, "/", null, 1),
                new Token(NUMBER, "4", "4", 1),
                new Token(SLASH, "/", null, 1),
                new Token(NUMBER, "5", "5", 1)
        );
        PrimaryFactorOperator processor =
                new PrimaryFactorOperator(tokens, 1);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(/ (/ (/ 2 3) 4) 5)");
    }
}

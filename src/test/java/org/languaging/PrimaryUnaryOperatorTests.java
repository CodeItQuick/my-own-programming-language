package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.languaging.TokenType.*;

class PrimaryUnaryOperatorTests {
    @Test
    public void givenUnaryOperatorUnaryCanBeProcessed() {
        List<Token> tokens = List.of(
                new Token(MINUS, "-", null, 1),
                new Token(NUMBER, null, "1", 1)
        );
        PrimaryUnaryOperator processor =
                new PrimaryUnaryOperator(tokens, 0);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(- 1)");
    }
    @Test
    public void givenUnaryNotOperatorUnaryCanBeProcessed() {
        List<Token> tokens = List.of(
                new Token(BANG, "!", null, 1),
                new Token(TRUE, null, "true", 1)
        );
        PrimaryUnaryOperator processor =
                new PrimaryUnaryOperator(tokens, 0);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(! true)");
    }
    @Test
    public void givenUnaryOperatorProcessorCorrectlyConverts() {
        List<Token> tokens = List.of(
                new Token(MINUS, "-", null, 1),
                new Token(NUMBER, null, "1", 1)
        );
        PrimaryProcessing processor =
                new PrimaryProcessing(tokens, 0);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(- 1)");
    }
    @Test
    public void givenDoubleUnaryOperatorProcessorCorrectlyConverts() {
        List<Token> tokens = List.of(
                new Token(MINUS, "-", null, 1),
                new Token(MINUS, "-", null, 1),
                new Token(NUMBER, null, "1", 1)
        );
        PrimaryProcessing processor =
                new PrimaryProcessing(tokens, 0);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(- (- 1))");
    }
    @Test
    public void givenTripleUnaryOperatorProcessorCorrectlyConverts() {
        List<Token> tokens = List.of(
                new Token(MINUS, "-", null, 1),
                new Token(MINUS, "-", null, 1),
                new Token(MINUS, "-", null, 1),
                new Token(NUMBER, null, "1", 1)
        );
        PrimaryProcessing processor =
                new PrimaryProcessing(tokens, 0);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(- (- (- 1)))");
    }
    @Test
    public void givenTripleBangUnaryOperatorProcessorCorrectlyConverts() {
        List<Token> tokens = List.of(
                new Token(BANG, "!", null, 1),
                new Token(BANG, "!", null, 1),
                new Token(BANG, "!", null, 1),
                new Token(TRUE, "true", "true", 1)
        );
        PrimaryProcessing processor =
                new PrimaryProcessing(tokens, 0);
        Expr expression = processor.process();

        assertThat(expression).isInstanceOf(Expr.class);
        String result = new AstPrinter().print(expression);
        assertThat(result).isEqualTo("(! (! (! true)))");
    }
    @Test
    public void givenRegularExpressionProcessorReturnsNull() {
        List<Token> tokens = List.of(
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1),
                new Token(SEMICOLON, null, ";", 1)
        );
        PrimaryUnaryOperator processor =
                new PrimaryUnaryOperator(tokens, 0);
        Expr process = processor.process();

        assertThat(process).isNull();
    }

}
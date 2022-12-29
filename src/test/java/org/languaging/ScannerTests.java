package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.languaging.TokenType.*;

public class ScannerTests {

    @Test
    public void GivenScannerWhenPlusTokenEnteredCanParseTokenType() {
        Scanner scanner = new Scanner("+");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of(
                        PLUS + " + null",
                        EOF + "  null" ));
    }
    @Test
    public void GivenScannerWhenMinusTokenEnteredCanParseTokenType() {
        Scanner scanner = new Scanner("-");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of(
                        MINUS + " - null",
                        EOF + "  null" ));
    }
    @Test
    public void WhenAdditionEnteredCanParseTokenType() {
        Scanner scanner = new Scanner("1+2;");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of("NUMBER 1 1.0", "PLUS + null", "NUMBER 2 2.0", "SEMICOLON ; null", "EOF  null"));
    }
    @Test
    public void WhenComplexExpressionEnteredCanParseTokenTypes() {
        Scanner scanner = new Scanner(
                "if (1 > 2) { var x = x + 1; }");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of(
                        "IF if null",
                        "LEFT_PAREN ( null",
                        "NUMBER 1 1.0",
                        "GREATER > null",
                        "NUMBER 2 2.0",
                        "RIGHT_PAREN ) null",
                        "LEFT_BRACE { null",
                        "VAR var null",
                        "IDENTIFIER x null",
                        "EQUAL = null",
                        "IDENTIFIER x null",
                        "PLUS + null",
                        "NUMBER 1 1.0",
                        "SEMICOLON ; null",
                        "RIGHT_BRACE } null",
                        "EOF  null"));
    }
    @Test
    public void GivenScannerWhenKeywordEnteredCanParseTokenType() {
        Scanner scanner = new Scanner("var helloWorld");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of("VAR var null", "IDENTIFIER helloWorld null", "EOF  null"));
    }
    @Test
    public void WhenForLoopGivenCanParseTokenType() {
        Scanner scanner = new Scanner(
                "for ( var i = 0; i < 1; i++) " +
                "{ }");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of("FOR for null",
                        "LEFT_PAREN ( null",
                        "VAR var null",
                        "IDENTIFIER i null",
                        "EQUAL = null",
                        "NUMBER 0 0.0",
                        "SEMICOLON ; null",
                        "IDENTIFIER i null",
                        "LESS < null",
                        "NUMBER 1 1.0",
                        "SEMICOLON ; null",
                        "IDENTIFIER i null",
                        "PLUS + null",
                        "PLUS + null",
                        "RIGHT_PAREN ) null",
                        "LEFT_BRACE { null",
                        "RIGHT_BRACE } null",
                        "EOF  null"));
    }
    @Test
    public void WhenExpressionEnteredCanParseTokenType() {
        Scanner scanner = new Scanner("var helloWorld = \" Hello World \";");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of(
                        "VAR var null",
                        "IDENTIFIER helloWorld null",
                        "EQUAL = null",
                        "STRING \" Hello World \"  Hello World ",
                        "SEMICOLON ; null",
                        "EOF  null"));
    }
}

package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.languaging.TokenType.*;
import static org.languaging.TokenType.CLASS;

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
    public void WhenClassDeclaredCanParseTokens() {
        Scanner scanner = new Scanner(
                "class HelloWorld { " +
                        "HelloWorld() { } " +
                        "hello() { print \"World\"; } " +
                        "} ");
        List<Token> tokens = scanner.scanTokens();
        assertThat(tokens.get(0).toString()).isEqualTo("CLASS class null"); //,
        assertThat(tokens.get(1).toString()).isEqualTo("IDENTIFIER HelloWorld null");
        assertThat(tokens.get(2).toString()).isEqualTo("LEFT_BRACE { null");
        assertThat(tokens.get(3).toString()).isEqualTo("IDENTIFIER HelloWorld null");
        assertThat(tokens.get(4).toString()).isEqualTo("LEFT_PAREN ( null");
        assertThat(tokens.get(5).toString()).isEqualTo("RIGHT_PAREN ) null");
        assertThat(tokens.get(7).toString()).isEqualTo("RIGHT_BRACE } null");
        assertThat(tokens.get(8).toString()).isEqualTo("IDENTIFIER hello null");
        assertThat(tokens.get(9).toString()).isEqualTo("LEFT_PAREN ( null");
        assertThat(tokens.get(10).toString()).isEqualTo("RIGHT_PAREN ) null");
        assertThat(tokens.get(11).toString()).isEqualTo("LEFT_BRACE { null");
        assertThat(tokens.get(12).toString()).isEqualTo("PRINT print null");
        assertThat(tokens.get(13).toString()).isEqualTo("STRING \"World\" World");
        assertThat(tokens.get(14).toString()).isEqualTo("SEMICOLON ; null");
        assertThat(tokens.get(15).toString()).isEqualTo("RIGHT_BRACE } null");
        assertThat(tokens.get(16).toString()).isEqualTo("RIGHT_BRACE } null");
        assertThat(tokens.get(17).toString()).isEqualTo("EOF  null");
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

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
                .isEqualTo(List.of("NUMBER 1 1.0",
                        "PLUS 1+ null",
                        "NUMBER 1+2 2.0",
                        "SEMICOLON 1+2; null",
                        "EOF  null"));
    }
    @Test
    public void WhenComplexExpressionEnteredCanParseTokenTypes() {
        Scanner scanner = new Scanner("if (1 > 2) { var x = x + 1; }");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of(
                        "IF if null",
                        "LEFT_PAREN if ( null",
                        "NUMBER if (1 1.0",
                        "GREATER if (1 > null",
                        "NUMBER if (1 > 2 2.0",
                        "RIGHT_PAREN if (1 > 2) null",
                        "LEFT_BRACE if (1 > 2) { null",
                        "IDENTIFIER if (1 > 2) { var null",
                        "IDENTIFIER if (1 > 2) { var x null",
                        "EQUAL if (1 > 2) { var x = null",
                        "IDENTIFIER if (1 > 2) { var x = x null",
                        "PLUS if (1 > 2) { var x = x + null",
                        "NUMBER if (1 > 2) { var x = x + 1 1.0",
                        "SEMICOLON if (1 > 2) { var x = x + 1; null",
                        "RIGHT_BRACE if (1 > 2) { var x = x + 1; } null",
                        "EOF  null"));
    }
    @Test
    public void GivenScannerWhenKeywordEnteredCanParseTokenType() {
        Scanner scanner = new Scanner("var helloWorld");

        List<String> tokens = scanner.scanTokens().stream().map(
                x -> x.toString()).collect(Collectors.toList());

        assertThat(tokens)
                .isEqualTo(List.of(
                        VAR + " var null",
                        IDENTIFIER + " var helloWorld null",
                        "EOF  null"));
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
                        "LEFT_PAREN for ( null",
                        "IDENTIFIER for ( var null",
                        "IDENTIFIER for ( var i null",
                        "EQUAL for ( var i = null",
                        "NUMBER for ( var i = 0 0.0",
                        "SEMICOLON for ( var i = 0; null",
                        "IDENTIFIER for ( var i = 0; i null",
                        "LESS for ( var i = 0; i < null",
                        "NUMBER for ( var i = 0; i < 1 1.0",
                        "SEMICOLON for ( var i = 0; i < 1; null",
                        "IDENTIFIER for ( var i = 0; i < 1; i null",
                        "PLUS for ( var i = 0; i < 1; i+ null",
                        "PLUS for ( var i = 0; i < 1; i++ null",
                        "RIGHT_PAREN for ( var i = 0; i < 1; i++) null",
                        "LEFT_BRACE for ( var i = 0; i < 1; i++) { null",
                        "RIGHT_BRACE for ( var i = 0; i < 1; i++) { } null",
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
                        "IDENTIFIER var helloWorld null",
                        "EQUAL var helloWorld = null",
                        "STRING var helloWorld = \" Hello World \" var helloWorld = \" Hello World \"",
                        "SEMICOLON var helloWorld = \" Hello World \"; null",
                        "EOF  null"));
    }
}

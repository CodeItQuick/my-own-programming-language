package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ScannerScanTokensTests {

    @Test
    void whenTokenIsScannedThenCanDetermineEnd() {
        Scanner scanner = new Scanner("+");

        scanner.scanToken();

        boolean atEnd = scanner.isAtEnd();
        assertThat(atEnd).isTrue();
    }
    @Test
    void whenEOFTokenIsAddedThenTokensIncludesEOF() {
        Scanner scanner = new Scanner("+");
        scanner.scanToken();


        List<Token> tokens = scanner.retrieveTokens();

        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("PLUS + null")
        );
    }
    @Test
    void whenStringIsAddedThenTokensCanParse() {
        Scanner scanner = new Scanner("\"text in here\"");
        scanner.scanToken();

        List<Token> tokens = scanner.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("STRING \"text in here\" text in here")
        );
    }
    @Test
    void whenNumberIsAddedThenTokensCanParse() {
        Scanner scanner = new Scanner("7");
        scanner.scanToken();

        List<Token> tokens = scanner.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("NUMBER 7 7.0")
        );
    }
    // TODO: Is this right? +2?
    @Test
    void whenExpressionIsAddedThenTokensCanParse() {
        Scanner scanner = new Scanner("7+2");
        scanner.scanToken();
        scanner.scanToken();

        List<Token> tokens = scanner.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of(
                                "NUMBER 7 7.0",
                                "PLUS 7+ null")
        );
    }
    @Test
    void whenComplexExpressionIsAddedThenTokensCanParse() {
        Scanner scanner = new Scanner("var");

        scanner.scanToken();

        List<Token> tokens = scanner.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("VAR var null")
        );
    }
}
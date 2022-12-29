package org.languaging;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ScannerServiceTests {

    @Test
    void whenTokenIsScannedThenCanDetermineEnd() {
        ScannerService scannerService = new ScannerService("+");

        scannerService.scanToken();

        boolean atEnd = scannerService.isAtEnd();
        assertThat(atEnd).isTrue();
    }
    @Test
    void whenEOFTokenIsAddedThenTokensIncludesEOF() {
        ScannerService scannerService = new ScannerService("+");
        scannerService.scanToken();

        scannerService.addEOFToken();

        List<Token> tokens = scannerService.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("PLUS + null", "EOF  null")
        );
    }
    @Test
    void whenStringIsAddedThenTokensCanParse() {
        ScannerService scannerService = new ScannerService("\"text in here\"");
        scannerService.scanToken();

        List<Token> tokens = scannerService.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("STRING \"text in here\" \"text in here\"")
        );
    }
    @Test
    void whenNumberIsAddedThenTokensCanParse() {
        ScannerService scannerService = new ScannerService("7");
        scannerService.scanToken();

        List<Token> tokens = scannerService.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("NUMBER 7 7.0")
        );
    }
    // TODO: Is this right? +2?
    @Test
    void whenExpressionIsAddedThenTokensCanParse() {
        ScannerService scannerService = new ScannerService("7+2");
        scannerService.scanToken();
        scannerService.scanToken();
        scannerService.scanToken();

        List<Token> tokens = scannerService.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of(
                                "NUMBER 7 7.0",
                                "PLUS 7+ null",
                                "NUMBER 7+2 2.0")
        );
    }
    @Test
    void whenComplexExpressionIsAddedThenTokensCanParse() {
        ScannerService scannerService = new ScannerService("var");

        scannerService.scanToken();

        List<Token> tokens = scannerService.retrieveTokens();
        List<String> tokensStringList = tokens.stream().map(
                x -> x.toString()).collect(Collectors.toList());
        assertThat(tokensStringList).isEqualTo(
                        List.of("VAR var null")
        );
    }
}
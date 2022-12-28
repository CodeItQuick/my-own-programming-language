package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.languaging.TokenType.*;
import static org.languaging.TokenType.STRING;

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
    public void GivenScannerWhenPrintingThenTwoTokensProduced() {
        Scanner scanner = new Scanner("print 1");

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).hasSize(3);
        assertThat(tokens.get(0).lexeme).isEqualTo("print");
        assertThat(tokens.get(0).type).isEqualTo(PRINT);
        assertThat(tokens.get(1).type).isEqualTo(NUMBER);
        assertThat(tokens.get(2).type).isEqualTo(EOF);
    }
    @Test
    public void GivenScannerWhenThenTwoTokensProduced() {
        Scanner scanner = new Scanner("1");

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).hasSize(2);
    }
    @Test
    public void printScannedProducesOnePrintKeywordToken() {
        Scanner scanner = new Scanner("print");

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).hasSize(2);
    }
    @Test
    public void printScannedProducesPrintAndStringTokens() {
        Scanner scanner = new Scanner("print \"hello world\"");

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens.get(0).type).isEqualTo(PRINT);
        assertThat(tokens.get(1).type).isEqualTo(STRING);
        assertThat(tokens.get(1).literal).isEqualTo("hello world");
    }
    // Saff Squeeze
    @Test
    public void printScannedIdentifierProducesPrintAndStringTokens() {
        ScannerService scannerService = new ScannerService("print \"hello world\";");
        scannerService.isAtEnd();
        scannerService.setStartEqualCurrent();
        scannerService.advance();
        scannerService.identifier();
        scannerService.setStartEqualCurrent();
        scannerService.advance();
        scannerService.advance();
        scannerService.advance();
        scannerService.identifier();
        scannerService.advance();
        scannerService.advance();
        scannerService.advance();
        scannerService.identifier();
        List<Token> tokens = scannerService.retrieveTokens();
        assertThat(tokens).hasSize(3);
        assertThat(tokens.get(0).type).isEqualTo(PRINT);
        assertThat(tokens.get(0).lexeme).isEqualTo("print");
        assertThat(tokens.get(1).type).isEqualTo(IDENTIFIER);
        assertThat(tokens.get(1).lexeme).isEqualTo("hello");
        assertThat(tokens.get(1).type).isEqualTo(IDENTIFIER);
        assertThat(tokens.get(1).lexeme).isEqualTo("world");
    }

}

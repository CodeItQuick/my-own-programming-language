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
}

package org.languaging;

import java.util.List;

import static org.languaging.TokenType.*;

class Scanner {
    private final ScannerService scannerService;

    Scanner(String source) {
        this.scannerService = new ScannerService(source);
    }

    List<Token> scanTokens() {
        while (!scannerService.isAtEnd()) {
            // We are at the beginning of the next lexeme.
            scannerService.setStartEqualCurrent();
            scannerService.scanToken();
        }

        scannerService.addEOFToken();
        return scannerService.retrieveTokens();
    }


}

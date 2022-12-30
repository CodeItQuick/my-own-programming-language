package org.languaging;

public class ScanState {
    private int current = 0;
    private int start = 0;
    private int line = 1;
    private final String source;

    public ScanState(String source) {

        this.source = source;
    }

    public char advance() {
        return source.charAt(current++);
    }

    void startAtCurrent() {
        start = current;
    }

    String substring() {
        return source.substring(start, current);
    }

    String substringQuotesRemoved() {
        return source.substring(start + 1, current - 1);
    }

    boolean isCurrentNotEqualTo(char expected) {
        return source.charAt(current) != expected;
    }

    void advanceCurrent() {
        current++;
    }

    char currentCharacter() {
        return source.charAt(current);
    }

    boolean isPastEnd() {
        return current + 1 >= source.length();
    }

    char charAfterCurrent() {
        return source.charAt(current + 1);
    }

    //< is-digit
//> is-at-end
    boolean isAtEnd() {
        return current >= source.length();
    }

    //< match
//> peek
    char peek() {
        if (isAtEnd()) return '\0';
        return currentCharacter();
    }

    //< peek
//> peek-next
    char peekNext() {
        if (isPastEnd()) return '\0';
        return charAfterCurrent();
    } // [peek-next]

    //< string
//> match
    boolean match(char expected) {
        if (isAtEnd()) return false;
        if (isCurrentNotEqualTo(expected)) return false;

        advanceCurrent();
        return true;
    }

    String extractString() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {

            throw new RuntimeException("Unterminated string");
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        return substringQuotesRemoved();
    }

    int line() {
        return line;
    }

    void advanceLine() {
        line++;
    }

    void advanceToEndOfNumber() {
        while (Scanner.isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && Scanner.isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (Scanner.isDigit(peek())) advance();
        }
    }

    void advanceToEndOfAlphaNumeric() {
        while (Scanner.isAlphaNumeric(peek())) advance();
    }

    //< is-at-end
//> advance-and-add-token

}

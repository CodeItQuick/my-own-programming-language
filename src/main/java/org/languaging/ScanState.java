package org.languaging;

public class ScanState {
    public int current = 0;
    public String source;
    public int start = 0;
    public int line = 1;

    public ScanState(String source) {

        this.source = source;
    }

    //< is-at-end
//> advance-and-add-token
    char advance() {
        return source.charAt(current++);
    }

    //< is-digit
//> is-at-end
    boolean isAtEnd() {
        return current >= source.length();
    }

    //< string
//> match
    boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    //< match
//> peek
    char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    //< peek
//> peek-next
    char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    } // [peek-next]

    int retrieveLineNumber() {
        return line;
    }

    String extractIdentifier() {
        while (Scanner.isAlphaNumeric(peek())) advance();

/* Scanning identifier < Scanning keyword-type
    addToken(IDENTIFIER);
*/
//> keyword-type
        String text = source.substring(start, current);
        return text;
    }

    void extractDigit() {
        while (Scanner.isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && Scanner.isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (Scanner.isDigit(peek())) advance();
        }
    }

    String retrieveSubstring() {
        return source.substring(start, current);
    }

    String extractString() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return null;
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        return value;
    }

    void assignStartToCurrent() {
        start = current;
    }
}

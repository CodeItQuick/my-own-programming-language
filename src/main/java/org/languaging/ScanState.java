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
}

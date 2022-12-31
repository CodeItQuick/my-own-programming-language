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
}

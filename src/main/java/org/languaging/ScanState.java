package org.languaging;

public class ScanState {
    public String source;
    public int start = 0;
    public int current = 0;
    public int line = 1;

    public ScanState(String source) {

        this.source = source;
    }
}

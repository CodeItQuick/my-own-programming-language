package org.languaging;

import java.util.Objects;

public class Tokens {
    public TokenType tokenOne;
    public TokenType tokenTwo;

    public Tokens(TokenType tokenOne, TokenType tokenTwo) {
        this.tokenOne = tokenOne;

        this.tokenTwo = tokenTwo;
    }

    public String toString() {
        return tokenOne.toString() + " " + tokenTwo.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tokens tokens = (Tokens) o;
        return tokenOne == tokens.tokenOne && tokenTwo == tokens.tokenTwo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenOne, tokenTwo);
    }
}

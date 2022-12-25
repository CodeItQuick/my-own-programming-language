package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.languaging.TokenType.*;

class ParserTest {

    @Test
    public void GivenEqualityCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(MINUS,null,"-",1),
                new Token(NUMBER,null,"1",1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);

    }
    @Test
    public void GivenEqualityOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(TRUE,null,"true",1),
                new Token(EQUAL_EQUAL,null,"==",1),
                new Token(FALSE,null,"false",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);

    }
    @Test
    public void GivenComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(GREATER,null,">",1),
                new Token(NUMBER,null,"2",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);

    }
    @Test
    public void GivenTermOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(MINUS,null,"-",2),
                new Token(NUMBER,null,"1",3),
                new Token(SEMICOLON,null,";",3)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);

    }
    @Test
    public void GivenUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(MINUS,null,"-",1),
                new Token(NUMBER,null,"1",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Unary.class);

    }
}
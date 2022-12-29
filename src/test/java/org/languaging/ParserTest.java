package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.languaging.TokenType.*;

class ParserTest {

    // Subtraction
    @Test
    public void GivenSubtractionSemiColonCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,"2",1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 1 2)");

    }
    @Test
    public void GivenTermEOFOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,"3",1),
                new Token(EOF,null,"EOF",1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 1 3)");

    }
    @Test
    public void GivenTermOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,"3",1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 1 3)");

    }
    // Addition
    @Test
    public void GivenAdditionCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(PLUS,"+",null,1),
                new Token(NUMBER,null,"3",1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(+ 1 3)");

    }
    // Equality
    @Test
    public void GivenEqualityOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(TRUE,null,"true",1),
                new Token(EQUAL_EQUAL,"==",null,1),
                new Token(FALSE,null,"false",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(== true false)");

    }
    @Test
    public void GivenNotEqualityOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(FALSE,null,"false",1),
                new Token(BANG_EQUAL,"!=",null,1),
                new Token(TRUE,null,"true",1),
                new Token(SEMICOLON,null, ";",1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(!= false true)");

    }
    // Greater Than
    @Test
    public void GivenComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(GREATER,">",null,1),
                new Token(NUMBER,null,"2",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(> 1 2)");

    }
    // Unary
    @Test
    public void GivenUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,"7",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Unary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 7)");

    }
    @Test
    public void GivenNotUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(BANG,"!",null,1),
                new Token(TRUE,null,"true",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Unary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(! true)");

    }
    @Test
    public void GivenRecursiveNotUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(BANG,"!",null,1),
                new Token(BANG,"!",null,1),
                new Token(TRUE,null,"true",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.parse();

        assertThat(expression).isInstanceOf(Expr.Unary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(! (! true))");

    }
}
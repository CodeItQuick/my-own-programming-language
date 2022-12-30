package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.languaging.TokenType.*;

class ParserTest {

    //ClassAndMethod hello world example
    @Test
    public void whenClassAndMethodParsedOutputCorrect() {
        Scanner scanner = new Scanner(
                "class HelloWorld { " +
                        "HelloWorld() { } " +
                        "hello() { print \"World\"; } " +
                        "} ");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statements.get(0))).isEqualTo(
                "(class HelloWorld (fun HelloWorld() ) (fun hello() (print World)))");

    }

    // Subtraction
    @Test
    public void GivenSubtractionSemiColonCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,2.0,1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 1.0 2.0)");

    }
    @Test
    public void GivenTermEOFOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,3.0,1),
                new Token(EOF,null,"EOF",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 1.0 3.0)");

    }
    @Test
    public void GivenTermOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,3.0,1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 1.0 3.0)");

    }
    // Addition
    @Test
    public void GivenAdditionCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1),
                new Token(PLUS,"+",null,1),
                new Token(NUMBER,null,3.0,1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(+ 1.0 3.0)");

    }
    // Equality
    @Test
    public void GivenEqualityOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(TRUE,null,true,1),
                new Token(EQUAL_EQUAL,"==",null,1),
                new Token(FALSE,null,false,1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.expression();

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

        Expr expression = parser.expression();

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

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(> 1 2)");

    }
    // Greater Than
    @Test
    public void GivenJustComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1),
                new Token(GREATER,">",null,1),
                new Token(NUMBER,null,"2",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(> 1 2)");

    }
    @Test
    public void GivenGreaterEqualComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1),
                new Token(GREATER_EQUAL,">=",null,1),
                new Token(NUMBER,null,2.0,1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(>= 1.0 2.0)");

    }
    @Test
    public void GivenLessEqualComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"4",1),
                new Token(LESS_EQUAL,"=<",null,1),
                new Token(NUMBER,null,"7",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(=< 4 7)");

    }

    // Factor
    @Test
    public void GivenDivisionCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"4",1),
                new Token(SLASH,"/",null,1),
                new Token(NUMBER,null,"2",1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(/ 4 2)");

    }
    @Test
    public void GivenMultiplicationCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,5.0,1),
                new Token(STAR,"*",null,1),
                new Token(NUMBER,null,3.0,1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Binary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(* 5.0 3.0)");

    }
    // Unary
    @Test
    public void GivenUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,"7",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Unary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- 7)");

    }
    @Test
    public void GivenRecursiveUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(MINUS,"-",null,1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,"7",1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Unary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(- (- 7))");

    }
    @Test
    public void GivenNotUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(BANG,"!",null,1),
                new Token(TRUE,null,"true",1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Unary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(! true)");

    }
    @Test
    public void GivenRecursiveNotUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(BANG,"!",null,1),
                new Token(BANG,"!",null,1),
                new Token(TRUE,null,"true",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Unary.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(! (! true))");

    }
    @Test
    public void GivenPrimaryNumberCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,"1",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Literal.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("1");

    }
    @Test
    public void GivenPrimaryFalseCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(FALSE,null,"false",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Literal.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("false");

    }
    @Test
    public void GivenPrimaryNullCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NIL,null,"null",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Literal.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("nil");

    }
    @Test
    public void GivenPrimaryStringCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(STRING,null,"hello world",1)
        ));

        Expr expression = parser.expression();

        assertThat(expression).isInstanceOf(Expr.Literal.class);
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("hello world");

    }
    @Test
    public void GivenGroupingsCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,4.0,1),
                new Token(SLASH,"/",null,1),
                new Token(LEFT_PAREN,null,"(",1),
                new Token(NUMBER,null,2.0,1),
                new Token(PLUS,"+",null,1),
                new Token(NUMBER,null,5.0,1),
                new Token(STAR,"*",null,1),
                new Token(NUMBER,null,7.0,1),
                new Token(RIGHT_PAREN,null,")",1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(/ 4.0 (group (+ 2.0 (* 5.0 7.0))))");
        assertThat(expression).isInstanceOf(Expr.Binary.class);

    }
    @Test
    public void GivenNonExpressionReturnsNull() {
        Parser parser = new Parser(List.of(
                new Token(SLASH,"/",null,1),
                new Token(STAR,"*",null,1),
                new Token(SEMICOLON,null,";",1)
        ));

        assertThrows(Exception.class, () -> {
            parser.expression();
        });

    }
}
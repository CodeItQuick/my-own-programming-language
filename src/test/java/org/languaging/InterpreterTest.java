package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.languaging.TokenType.*;

class InterpreterTest {

    @Test
    public void whenSubtractionIsInterpretedOutputCorrect() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,"1",1.0,1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,"2",2.0,1),
                new Token(SEMICOLON,null,";",1),
                new Token(EOF,null,"EOF",1)
        ));
        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(-1.0);
    }
    @Test
    public void GivenAdditionCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1),
                new Token(PLUS,"+",null,1),
                new Token(NUMBER,null,3.0,1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(4.0);
    }
    @Test
    public void GivenEqualityOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(TRUE,null,true,1),
                new Token(EQUAL_EQUAL,"==",null,1),
                new Token(FALSE,null,false,1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(false);

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

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(true);

    }
    // Greater Than
    @Test
    public void GivenComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1),
                new Token(GREATER,">",null,1),
                new Token(NUMBER,null,2.0,1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(false);

    }
    @Test
    public void GivenGreaterEqualComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,2.0,1),
                new Token(GREATER_EQUAL,">=",null,1),
                new Token(NUMBER,null,2.0,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(true);

    }
    @Test
    public void GivenLessEqualComparisonOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,4.0,1),
                new Token(LESS_EQUAL,"=<",null,1),
                new Token(NUMBER,null,7.0,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(true);

    }
    @Test
    public void GivenDivisionCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,4.0,1),
                new Token(SLASH,"/",null,1),
                new Token(NUMBER,null,2.0,1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(2.0);

    }

    @Test
    public void GivenMultiplicationCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,5.0,1),
                new Token(STAR,"*",null,1),
                new Token(NUMBER,null,3.0,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(15.0);

    }

    // Unary
    @Test
    public void GivenUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,7.0,1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(-7.0);

    }
    @Test
    public void GivenRecursiveUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(MINUS,"-",null,1),
                new Token(MINUS,"-",null,1),
                new Token(NUMBER,null,7.0,1),
                new Token(EOF,null,null,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(7.0);

    }
    @Test
    public void GivenNotUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(BANG,"!",null,1),
                new Token(TRUE,null,"true",1),
                new Token(SEMICOLON,null,";",1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(false);

    }
    @Test
    public void GivenRecursiveNotUnaryOperatorCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(BANG,"!",null,1),
                new Token(BANG,"!",null,1),
                new Token(TRUE,null,"true",1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(true);

    }
    @Test
    public void GivenPrimaryNumberCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,1.0,1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(1.0);

    }
    @Test
    public void GivenPrimaryFalseCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(FALSE,null,"false",1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(false);

    }
    @Test
    public void GivenPrimaryNullCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NIL,null,"null",1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(null);

    }
    @Test
    public void GivenPrimaryStringCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(STRING,null,"hello world",1)
        ));

        Expr expression = parser.expression();

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo("hello world");

    }
    @Test
    public void GivenGroupingsCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(NUMBER,null,74.0,1),
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

        Interpreter interpreter = new Interpreter();
        assertThat(interpreter.evaluate(expression)).isEqualTo(2.0);
    }
    @Test
    public void GivenComplexExpressionCanParseExpression() {
        Parser parser = new Parser(List.of(
                new Token(IF, "if", null, 1),
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(NUMBER, "3", 3.0, 1),
                new Token(GREATER, ">", null, 1),
                new Token(NUMBER, "2", 2.0, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(LEFT_BRACE, "{", null, 1),
                new Token(VAR, "var", null, 1),
                new Token(IDENTIFIER, "x", null, 1),
                new Token(EQUAL, "=", null, 1),
                new Token(NUMBER, "2", 2.0, 1),
                new Token(PLUS, "+", null, 1),
                new Token(NUMBER, "1", 1.0, 1),
                new Token(SEMICOLON, ";", null, 1),
                new Token(RIGHT_BRACE, "}", null, 1),
                new Token(EOF, null, null, 1)
        ));

        List<Stmt> statement = parser.parse();

        Interpreter interpreter = new Interpreter();
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statement.get(0))).isEqualTo("(if (> 3.0 2.0) (block (var x = (+ 2.0 1.0))))");
        assertThat(statement.get(0).accept(interpreter)).isNull();
    }
    @Test
    public void WhenExpressionEnteredCanParseTokenType() {

        Parser parser = new Parser(List.of(
                        new Token(VAR, "var", null, 1),
                        new Token(IDENTIFIER, "helloWorld", null, 1),
                        new Token(EQUAL, "=", null, 1),
                        new Token(STRING, "\" Hello World \"",  " Hello World ", 1),
                        new Token(SEMICOLON, ";", null, 1),
                        new Token(EOF, null,  null, 1)));

        List<Stmt> statement = parser.parse();

        Interpreter interpreter = new Interpreter();
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statement.get(0))).isEqualTo("(var helloWorld =  Hello World )");
        assertThat(statement.get(0).accept(interpreter)).isNull();
    }
}
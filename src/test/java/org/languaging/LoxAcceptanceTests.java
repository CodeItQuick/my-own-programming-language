package org.languaging;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.languaging.TokenType.PRINT;
import static org.languaging.TokenType.STRING;

public class LoxAcceptanceTests {
    @Test
    public void CanParseSimpleNumberIntoExpression() {
        Scanner scanner = new Scanner("1;");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.expression();
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("1.0");

    }
    @Test
    public void CanParseAdditionIntoNumber() {
        Scanner scanner = new Scanner("1+2;");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.expression();
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("(+ 1.0 2.0)");

    }
    @Test
    public void CanParseHelloWorldIntoString() {
        Scanner scanner = new Scanner("\"hello world\";");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.expression();
        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(expression)).isEqualTo("hello world");
    }
    @Test
    public void CanExecutePrintStatement() {
        Scanner scanner = new Scanner("print \"hello world\";");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        parser.match(PRINT);
        Expr expression = parser.expression();
        AstPrinter astPrinter = new AstPrinter();
        assertThat(tokens.get(0).type).isEqualTo(PRINT);
        assertThat(tokens.get(1).type).isEqualTo(STRING);
        assertThat(astPrinter.print(expression)).isEqualTo("hello world");
    }
    @Test
    public void CanEvaluateHelloWorldPrintStatement() {
        Scanner scanner = new Scanner("print \"hello world\";");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        parser.match(PRINT);
        Interpreter interpreter = new Interpreter();
        Expr expression = parser.expression();

        Object evaluate = interpreter.evaluate(expression);

        assertThat(evaluate).isEqualTo("hello world");
    }
    @Test
    public void CanEvaluateAdditionPrintStatement() {
        Scanner scanner = new Scanner("print 1+2;");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        parser.match(PRINT);
        Interpreter interpreter = new Interpreter();
        Expr expression = parser.expression();

        Object evaluate = interpreter.evaluate(expression);

        assertThat(evaluate).isEqualTo(3.0);
    }
    @Test
    public void CanEvaluateDivisionPrintStatement() {
        Scanner scanner = new Scanner("print 4/2;");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        parser.match(PRINT);
        Interpreter interpreter = new Interpreter();
        Expr expression = parser.expression();

        Object evaluate = interpreter.evaluate(expression);

        assertThat(evaluate).isEqualTo(2.0);
    }
    @Test
    public void CanEvaluateMultiplicationPrintStatement() {
        Scanner scanner = new Scanner("print 3*7;");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        parser.match(PRINT);
        Interpreter interpreter = new Interpreter();
        Expr expression = parser.expression();

        Object evaluate = interpreter.evaluate(expression);

        assertThat(evaluate).isEqualTo(21.0);
    }
    @Test
    public void CanEvaluateGroupingAdditionMultiplicationPrintStatement() {
        Scanner scanner = new Scanner("print 3*(4+2);");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        parser.match(PRINT);
        Interpreter interpreter = new Interpreter();
        Expr expression = parser.expression();

        Object evaluate = interpreter.evaluate(expression);

        assertThat(evaluate).isEqualTo(18.0);
    }
}

package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Primarily "Sociable"/"Acceptance" tests, these are meant to test the overall program briefly
class LoxTests {

    @Test
    public void whenAdditionIsProcessedItOutputsCorrectStatement() {
        Scanner scanner = new Scanner("1+2;");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        interpreter.interpret(statements); //returns void, no need for this?!?!?

        AstPrinter astPrinter = new AstPrinter();
        String print = astPrinter.print(statements.get(0));
        assertThat(print).isEqualTo("(; (+ 1.0 2.0))");

    }
    @Test
    public void whenSubtractionAndIfStatementInterpretedOutputCorrect() {
        Scanner scanner = new Scanner("if(2 < 4) { print 4-2; }");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        String print = astPrinter.print(statements.get(0));
        assertThat(print).isEqualTo("(if (< 2.0 4.0) (block (print (- 4.0 2.0))))");

    }
    @Test
    public void whenClassAndMethodInterpretedOutputCorrect() {
        Scanner scanner = new Scanner(
                "class HelloWorld { " +
                        "HelloWorld() { } " +
                        "hello() { print \"World\"; } " +
                        "} " +
                        "var helloWorld = HelloWorld(); " +
                        "helloWorld.hello(); ");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        interpreter.interpret(statements); //returns void, no need for this?!?!?

        AstPrinter astPrinter = new AstPrinter();
        String print = astPrinter.print(statements.get(0));
        assertThat(print).isEqualTo("(class HelloWorld (fun HelloWorld() ) (fun hello() (print World)))");

    }
}
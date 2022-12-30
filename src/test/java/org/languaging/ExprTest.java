package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExprTest {
    @Test
    public void GivenATrueLiteralCanVisitExpression() {
        Expr.Literal trueLiteral = new Expr.Literal(true);
        Object acceptedLiteral = trueLiteral.accept(new Interpreter());
        assertThat(acceptedLiteral).isEqualTo(true);
    }
    @Test
    public void GivenANegativeUnaryCanVisitExpression() {
        Expr.Unary bangUnary = new Expr.Unary(
                new Token(TokenType.BANG, "!", "!", 1),
                new Expr.Literal(true)
                );
        Object acceptedLiteral = bangUnary.accept(new Interpreter());
        assertThat(acceptedLiteral).isEqualTo(false);
    }
    @Test
    public void GivenAVariableCanVisitExpression() {
        Stmt.Var statement = new Stmt.Var(
                new Token(TokenType.IDENTIFIER, "helloWorld", "helloWorld", 1),
                null
        );

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statement)).isEqualTo("(var helloWorld)");
    }
    @Test
    public void ForStatementCanBeParsed() {
        Scanner scanner = new Scanner("for (var i = 0; i < 1; i = i + 1) { }");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statements.get(0))).isEqualTo(
                "(block (var i = 0.0)" +
                        "(while (< i 1.0) " +
                        "(block (block )" +
                        "(; (= i (+ i 1.0))))))");
    }
    @Test
    public void IfStatementCanBeParsed() {
        Scanner scanner = new Scanner("if (1 < 2) { }");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statements.get(0))).isEqualTo(
                "(if (< 1.0 2.0) (block ))");
    }
    @Test
    public void IfStatementDeclaredCanBeAccepted() {
        Expr condition = new Expr.Binary(
                new Expr.Literal(true),
                new Token(TokenType.EQUAL_EQUAL, "==", null, 1),
                new Expr.Literal(true));
        Stmt thenBranch = new Stmt.Block(List.of());
        Stmt elseBranch = new Stmt.Block(List.of());

        Stmt.If ifStatement = new Stmt.If(condition, thenBranch, elseBranch);

        Void accept = ifStatement.accept(new Interpreter());

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(ifStatement)).isEqualTo(
                "(if-else (== true true) (block ) (block ))");
        assertThat(accept).isNull();
    }
    @Test
    public void PrintStatementCanBeParsed() {
        Scanner scanner = new Scanner("print \"Hello World\";");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statements.get(0))).isEqualTo(
                "(print Hello World)");
    }
    @Test
    public void ReturnStatementCanBeParsed() {
        Scanner scanner = new Scanner("fun hello() { return \"world\"; } ");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statements.get(0))).isEqualTo(
                "(fun hello() (return world))");
    }
    @Test
    public void WhileStatementCanBeParsed() {
        Scanner scanner = new Scanner("while (1 > 2) { }");
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        assertThat(astPrinter.print(statements.get(0))).isEqualTo(
                "(while (> 1.0 2.0) (block ))");
    }
}
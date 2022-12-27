package org.languaging;

import java.util.List;

import static org.languaging.TokenType.*;

public class PrimaryUnaryOperator implements Processable {
    private List<Token> tokens;
    private int current;

    public PrimaryUnaryOperator(List<Token> tokens, int current) {
        this.tokens = tokens;
        this.current = current;
    }

    @Override
    public Expr process() {
        if (current + 1 >= tokens.size()) return null; // do not let out of bound exception

        // minus unary operator (method?)
        Token firstToken = tokens.get(current);
        Token secondToken = tokens.get(current + 1);
        // recursively determine second term
        if (firstToken.type == MINUS &&
            secondToken.type == MINUS) {


            PrimaryUnaryOperator unaryGenerator = new PrimaryUnaryOperator(
                    tokens,
                    current + 1);

            Expr right = unaryGenerator.process();

            Expr.Unary expression = new Expr.Unary(
                    firstToken,
                    right);
            return expression;
        }

        if (firstToken.type == MINUS &&
                secondToken.type == NUMBER) {
            // TODO: put the second term through primary processing again?
            return new Expr.Unary(
                    firstToken,
                    new Expr.Literal(secondToken.literal));
        }

        // negated boolean unary operator (method?)
        if(firstToken.type == BANG &&
          secondToken.type == BANG) {

            PrimaryUnaryOperator unaryGenerator = new PrimaryUnaryOperator(
                    tokens,
                    current + 1);

            Expr right = unaryGenerator.process();
            return new Expr.Unary(firstToken, right);
        }
        if(firstToken.type == BANG &&
          (secondToken.type == TRUE || secondToken.type == FALSE)) {
            return new Expr.Unary(firstToken, new Expr.Literal(secondToken.literal));
        }

        // couldn't parse return null
        return null;
    }
}

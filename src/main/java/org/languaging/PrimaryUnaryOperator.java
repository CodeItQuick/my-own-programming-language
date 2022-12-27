package org.languaging;

import java.util.*;
import java.util.function.Function;

import static org.languaging.TokenType.*;

public class PrimaryUnaryOperator implements Processable {
    private List<Token> tokens;
    private int current;
    // firstToken.type, secondToken.type
    private HashMap<String, Function> processTokenTypes;

    public PrimaryUnaryOperator(List<Token> tokens, int current) {
        this.tokens = tokens;
        this.current = current;
        processTokenTypes = new HashMap<>();
        processTokenTypes.put(MINUS.toString() + MINUS.toString(), this::recursiveProcessExpression);
        processTokenTypes.put(BANG.toString() + BANG.toString(), this::recursiveProcessExpression);
        processTokenTypes.put(BANG.toString() + FALSE.toString(), this::baseCaseUnary);
        processTokenTypes.put(MINUS.toString() + NUMBER.toString(), this::baseCaseUnary);
        processTokenTypes.put(BANG.toString() + TRUE.toString(), this::baseCaseUnary);
    }

    @Override
    public Expr process() {
        if (current + 1 >= tokens.size()) return null; // do not let out of bound exception

        Function matchedTokenTypesFn = processTokenTypes.get(
                tokens.get(current).type.toString() + // first token stringified
                tokens.get(current + 1).type.toString()); // second token stringified
        // couldn't parse return null
        if (
            matchedTokenTypesFn == null
        ) return null;

        return (Expr) matchedTokenTypesFn.apply(tokens);

    }

    public Expr baseCaseUnary(Object tokens) {
        Expr.Unary unary = new Expr.Unary(
                ((List<Token>) tokens).get(current),
                new Expr.Literal((((List<Token>) tokens).get(current + 1)).literal));
        return unary;
    }

    private Expr recursiveProcessExpression(Object firstToken) {
        PrimaryUnaryOperator unaryGenerator = new PrimaryUnaryOperator(
                tokens,
                current + 1);

        Expr right = unaryGenerator.process();

        return new Expr.Unary(
                ((List<Token>) firstToken).get(current),
                right);
    }
}

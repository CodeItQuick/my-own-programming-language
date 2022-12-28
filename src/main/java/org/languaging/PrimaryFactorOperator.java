package org.languaging;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static org.languaging.TokenType.*;

public class PrimaryFactorOperator implements Processable {
    private List<Token> tokens;
    private int current;
    // firstToken.type, secondToken.type
    private HashMap<String, Function> processTokenTypes;

    public PrimaryFactorOperator(List<Token> tokens, int current) {
        this.tokens = tokens;
        this.current = current;
        processTokenTypes = new HashMap<>();
        processTokenTypes.put(SLASH.toString() + NUMBER + SLASH, this::recursiveProcessExpression);
        processTokenTypes.put(STAR.toString() + NUMBER + STAR, this::recursiveProcessExpression);
        processTokenTypes.put(STAR.toString(), this::baseCaseUnary);
        processTokenTypes.put(SLASH.toString(), this::baseCaseUnary);
    }

    @Override
    public Expr process() {
        if (current + 1 >= tokens.size() && current != 0) return null; // do not let out of bound exception

        Function matchedTokenTypesFn = processTokenTypes.get(tokens.get(current).type.toString()); // second token stringified
        if (current + 2 < tokens.size()) {
            Function matchedMultipleTokenTypesFn = processTokenTypes.get(tokens.get(current).type.toString() + tokens.get(current + 1).type.toString() + tokens.get(current + 2).type.toString());
            if (matchedMultipleTokenTypesFn != null) {
                return (Expr) matchedMultipleTokenTypesFn.apply(tokens);
            }

        }
        // couldn't parse return null
        if (matchedTokenTypesFn == null) return null;

        return (Expr) matchedTokenTypesFn.apply(tokens);

    }

    public Expr baseCaseUnary(Object tokens) {
        return new Expr.Binary(
                new Expr.Literal(((List<Token>) tokens).get(current - 1).literal),
                ((List<Token>) tokens).get(current),
                new Expr.Literal(((List<Token>) tokens).get(current + 1).literal));
    }

    private Expr recursiveProcessExpression(Object tokensParam) {

        Expr left = baseCaseUnary(tokensParam);

        Expr right = new Expr.Literal(((List<Token>) tokensParam).get(current + 3).literal);
        return new Expr.Binary(left, ((List<Token>) tokensParam).get(current), right);
    }
}

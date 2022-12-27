package org.languaging;

import java.util.List;
import java.util.stream.Collectors;

public class PrimaryProcessing {
    private List<Processable> subExpressions;

    public PrimaryProcessing(List<Token> tokens, int current) {

        List<Token> groupingTokens = tokens.stream()
                .skip(current)
                .collect(Collectors.toList());
        subExpressions = List.of(
                new PrimaryUnaryOperator(tokens, current),
                new PrimaryParenthesisLiterals(
                        groupingTokens
                ),
                new PrimaryEqualityLiterals(
                        List.of(tokens.get(current))),
                new PrimaryNumberLiterals(
                        List.of(tokens.get(current)))
                );
    }

    public Expr process() {
        for (int i = 0; i < subExpressions.size(); i++) {
            Expr literal = subExpressions.get(i).process();
            if (literal != null) {
                return literal;
            }
        }
        return null;
    }

}

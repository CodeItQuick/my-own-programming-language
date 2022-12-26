package org.languaging;

import java.util.List;
import java.util.stream.Collectors;

public class PrimaryProcessing {
    private List<Consumable> subExpressions;

    public PrimaryProcessing(List<Token> tokens, int current) {

        List<Token> groupingTokens = tokens.stream()
                .skip(current)
                .collect(Collectors.toList());
        subExpressions = List.of(
                new SubExpressionLiteralEquality(
                        List.of(tokens.get(current))),
                new SubExpressionLiteralNumberString(
                        List.of(tokens.get(current))),
                new SubExpressionLiteralParenthesis(
                        groupingTokens
                )
        );
    }

    public Expr process() {
        for (Consumable subExpressionLiteral: subExpressions) {
            Expr literal = subExpressionLiteral.process();
            if (literal != null) {
                return literal;
            }
        }
        return null;
    }

}

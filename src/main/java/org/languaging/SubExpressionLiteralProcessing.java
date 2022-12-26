package org.languaging;

import java.util.List;

public class SubExpressionLiteralProcessing {
    private List<Consumable> subExpressions;

    public SubExpressionLiteralProcessing(List<Consumable> subExpressions) {

        this.subExpressions = subExpressions;
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

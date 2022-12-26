package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.languaging.TokenType.*;
import static org.languaging.TokenType.EOF;

class SubExpressionLiteralProcessingTest {
    @Test
    public void givenAllSubexpressionsCanParse() {
        List<Token> tokens = List.of(
                new Token(LEFT_PAREN, null, "(", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(MINUS, null, "-", 1),
                new Token(NUMBER, null, "1", 1),
                new Token(RIGHT_PAREN, null, ")", 1)
        );
        SubExpressionLiteralProcessing processor =
                new SubExpressionLiteralProcessing(tokens, 0);
        Expr process = processor.process();

        assertThat(process).isInstanceOf(Expr.class);
    }

}
package org.languaging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.languaging.TokenType.*;

public class PrimaryFactorOperatorTests {

    // ZOMBIES
//    @Test
//    public void givenMultiplicationCanParseResult() {
//        List<Token> tokens = List.of(
//                new Token(NUMBER, null, "2", 1),
//                new Token(STAR, "*", null, 1),
//                new Token(NUMBER, null, "3", 1)
//        );
//        PrimaryFactorOperator processor =
//                new PrimaryFactorOperator(tokens, 0);
//        Expr expression = processor.process();
//
//        assertThat(expression).isInstanceOf(Expr.class);
//        String result = new AstPrinter().print(expression);
//        assertThat(result).isEqualTo("(- 1)");
//    }
}

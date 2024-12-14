package ch.mobi.email;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerTest {

    @Nested
    class next {

        @Test
        void it_should_get_expected_tokens() {

            var email = "hans.muster@muster.musterius.ch";

            var expectedTokens = List.of(
                    new Token(Token.Type.IDENT, "hans"),
                    new Token(Token.Type.PERIOD),
                    new Token(Token.Type.IDENT, "muster"),
                    new Token(Token.Type.AT),
                    new Token(Token.Type.IDENT, "muster"),
                    new Token(Token.Type.PERIOD),
                    new Token(Token.Type.IDENT, "musterius"),
                    new Token(Token.Type.PERIOD),
                    new Token(Token.Type.IDENT, "ch"),
                    new Token(Token.Type.EOF)
            );

            var scanner = new Scanner(email);

            // Act
            var tokens = scanAll(scanner);

            // Assert
            assertEquals(expectedTokens, tokens);
        }
    }

    private static List<Token> scanAll(Scanner scanner) {

        List<Token> tokens = new ArrayList<>();
        Token token;
        while (EnumSet.of(Token.Type.IDENT, Token.Type.PERIOD, Token.Type.AT).contains((token = scanner.next()).type())) {
            tokens.add(token);
        }
        tokens.add(token);
        return tokens;
    }
}

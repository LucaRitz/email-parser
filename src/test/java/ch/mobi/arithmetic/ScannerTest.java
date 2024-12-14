package ch.mobi.arithmetic;

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

            var email = "5 + 3 * 7";

            var expectedTokens = List.of(
                    new Token(Token.Type.NUMBER, 5.d),
                    new Token(Token.Type.PLUS),
                    new Token(Token.Type.NUMBER, 3.d),
                    new Token(Token.Type.MULTIPLY),
                    new Token(Token.Type.NUMBER, 7.d),
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
        while (!EnumSet.of(Token.Type.EOF, Token.Type.UNKNOWN).contains((token = scanner.next()).type())) {
            tokens.add(token);
        }
        tokens.add(token);
        return tokens;
    }
}

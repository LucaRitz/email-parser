package ch.mobi.email.solution;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerTest {

    @Nested
    class next {

        @ParameterizedTest
        @MethodSource("validEmail")
        void it_should_get_expected_tokens(String email, List<Token> expectedTokens) {

            var scanner = new Scanner(email);

            // Act
            var tokens = scanAll(scanner);

            // Assert
            assertEquals(expectedTokens, tokens);
        }

        private static Stream<Arguments> validEmail() {

            return Stream.of(
                    Arguments.of("hans.muster@muster.musterius.ch",
                            List.of(
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
                            ))
            );
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

package ch.mobi.email.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParserTest {

    private Parser parser;

    @Mock
    private Scanner scanner;

    @BeforeEach
    void setUp() {

        parser = new Parser(scanner);
    }

    @Nested
    class parse {

        @ParameterizedTest
        @MethodSource("validTokenSource")
        void it_should_get_expected_email(List<Token> tokens, Email expectedEmail) {

            mockScanner(tokens);

            // Act
            var email = parser.parse();

            // Assert
            assertNotNull(email);
            assertEquals(expectedEmail, email);
        }

        @Test
        void it_should_throw_exception_if_email_is_invalid() {

            when(scanner.next())
                    .thenReturn(new Token(Token.Type.IDENT, "hans"))
                    .thenReturn(new Token(Token.Type.PERIOD))
                    .thenReturn(new Token(Token.Type.IDENT, "muster"))
                    .thenReturn(new Token(Token.Type.AT))
                    .thenReturn(new Token(Token.Type.IDENT, "must"))
                    .thenReturn(new Token(Token.Type.PERIOD))
                    .thenReturn(new Token(Token.Type.PERIOD))
                    .thenReturn(new Token(Token.Type.IDENT, "ch"))
                    .thenReturn(new Token(Token.Type.EOF));

            // Act & Assert
            assertThrows(InvalidEmailException.class, () -> parser.parse());
        }

        private static Stream<Arguments> validTokenSource() {

            return Stream.of(
                    Arguments.of(List.of(
                            new Token(Token.Type.IDENT, "hans"),
                            new Token(Token.Type.PERIOD),
                            new Token(Token.Type.IDENT, "muster"),
                            new Token(Token.Type.AT),
                            new Token(Token.Type.IDENT, "must"),
                            new Token(Token.Type.PERIOD),
                            new Token(Token.Type.IDENT, "ch"),
                            new Token(Token.Type.EOF)
                    ), new Email(new Email.Address(List.of("hans", "muster")), new Email.Domain(List.of("must", "ch")))),
                    Arguments.of(List.of(
                            new Token(Token.Type.IDENT, "first"),
                            new Token(Token.Type.AT),
                            new Token(Token.Type.IDENT, "d2omain"),
                            new Token(Token.Type.PERIOD),
                            new Token(Token.Type.IDENT, "two"),
                            new Token(Token.Type.PERIOD),
                            new Token(Token.Type.IDENT, "ch"),
                            new Token(Token.Type.EOF)
                    ), new Email(new Email.Address(List.of("first")), new Email.Domain(List.of("d2omain", "two", "ch"))))
            );
        }
    }

    private void mockScanner(List<Token> tokens) {

        var mock = when(scanner.next());
        for(var token : tokens) {
            mock = mock.thenReturn(token);
        }
    }
}

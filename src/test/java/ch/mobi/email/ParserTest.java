package ch.mobi.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        @Test
        void it_should_get_expected_email() {

            when(scanner.next())
                    .thenReturn(new Token(Token.Type.IDENT, "hans"))
                    .thenReturn(new Token(Token.Type.PERIOD))
                    .thenReturn(new Token(Token.Type.IDENT, "muster"))
                    .thenReturn(new Token(Token.Type.AT))
                    .thenReturn(new Token(Token.Type.IDENT, "must"))
                    .thenReturn(new Token(Token.Type.PERIOD))
                    .thenReturn(new Token(Token.Type.IDENT, "ch"))
                    .thenReturn(new Token(Token.Type.EOF));

            // Act
            var email = parser.parse();

            // Assert
            assertNotNull(email);
            assertEquals("hans.muster", email.address());
            assertEquals("must.ch", email.domain());
        }
    }
}

package ch.mobi.arithmetic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        void it_should_get_expected_result() {

            when(scanner.next())
                    .thenReturn(new Token(Token.Type.NUMBER, 5))
                    .thenReturn(new Token(Token.Type.PLUS))
                    .thenReturn(new Token(Token.Type.NUMBER, 3))
                    .thenReturn(new Token(Token.Type.MULTIPLY))
                    .thenReturn(new Token(Token.Type.NUMBER, 7))
                    .thenReturn(new Token(Token.Type.EOF));

            // Act
            var result = parser.parse();

            // Assert
            assertEquals(26.d, result);
        }
    }
}

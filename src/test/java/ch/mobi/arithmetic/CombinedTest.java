package ch.mobi.arithmetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombinedTest {

    @Test
    void it_should_get_expected_advanced_result() {

        var input = "-(3   + 4) * 5 / (8 + 2)";

        var scanner = new Scanner(input);
        var parser = new Parser(scanner);

        // Act
        var result = parser.parse();

        // Assert
        assertEquals(-3.5d, result);
    }
}

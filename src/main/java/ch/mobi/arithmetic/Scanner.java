package ch.mobi.arithmetic;

import java.util.Set;

public class Scanner {

    // Grammar
    // Expr    = ["+"|"-"] Term {("+"|"-") Term} .
    // Term    = Factor {("*"|"/") Factor} .
    // Factor  = number | "(" Expr ")" .

    // Terminalsymbole: "+", "-", "*", "/", number, "(", ")", "eof"

    private static final char EOF = (char) -1;
    private static final Set<Character> DIGITS = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    private final String formula;

    private int currentIndex = -1;
    private char currentCharacter = ' ';

    public Scanner(String formula) {

        this.formula = formula;
    }

    public Token next() {

        var nextCharacter = nextCharacterSkipEmpty();
        return switch (nextCharacter) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> readNumber(nextCharacter);
            case '+' -> {
                nextCharacter();
                yield new Token(Token.Type.PLUS);
            }
            case '-' -> {
                nextCharacter();
                yield new Token(Token.Type.MINUS);
            }
            case '*' -> {
                nextCharacter();
                yield new Token(Token.Type.MULTIPLY);
            }
            case '/' -> {
                nextCharacter();
                yield new Token(Token.Type.DIVIDE);
            }
            case '(' -> {
                nextCharacter();
                yield new Token(Token.Type.LEFT_BRACKET);
            }
            case ')' -> {
                nextCharacter();
                yield new Token(Token.Type.RIGHT_BRACKET);
            }
            case EOF -> new Token(Token.Type.EOF);
            default -> new Token(Token.Type.UNKNOWN);
        };
    }

    private char nextCharacterSkipEmpty() {

        while (currentCharacter <= ' ') nextCharacter();
        return currentCharacter;
    }

    private char nextCharacter() {

        currentCharacter = currentIndex > formula.length() - 2 ? EOF : formula.charAt(++currentIndex);
        return currentCharacter;
    }

    private Token readNumber(char currentCharacter) {

        var ident = new StringBuilder("" + currentCharacter);
        var nextCharacter = nextCharacter();
        while (DIGITS.contains(nextCharacter)) {
            ident.append(nextCharacter);
            nextCharacter = nextCharacter();
        }

        try {
            var number = Integer.parseInt(ident.toString());
            return new Token(Token.Type.NUMBER, number);
        } catch (NumberFormatException exc) {
            // NOOP
        }
        return new Token(Token.Type.UNKNOWN);
    }
}

package ch.mobi.email;

import java.util.Set;

public class Scanner {

    // Grammar
    // EMail   = Address "@" Domain .
    // Address = Name ["." Name] .
    // Domain  = Name "." Name {"." Name} .
    // Name    = ident
    // ident   = letter {letter | digit}
    // letter  = "a",...,"z","A",...,"Z"
    // digit   = "0",...,"9"

    // Terminalsymbole: "ident", "@", ".", "eof"

    private static final char EOF = (char) -1;
    private static final Set<Character> LETTERS = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    private static final Set<Character> DIGITS = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    private final String email;

    private int currentIndex = -1;
    private char currentCharacter = ' ';

    Scanner(String email) {

        this.email = email;
    }

    public Token next() {

        var nextCharacter = nextCharacterSkipEmpty();
        return switch (nextCharacter) {
            case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' -> readName(nextCharacter);
            case '@' -> {
                nextCharacter();
                yield new Token(Token.Type.AT);
            }
            case '.' -> {
                nextCharacter();
                yield new Token(Token.Type.PERIOD);
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

        currentCharacter = currentIndex > email.length() - 2 ? EOF : email.charAt(++currentIndex);
        return currentCharacter;
    }

    private Token readName(char currentCharacter) {

        var ident = new StringBuilder("" + currentCharacter);
        var nextCharacter = nextCharacter();
        while (LETTERS.contains(nextCharacter) || DIGITS.contains(nextCharacter)) {
            ident.append(nextCharacter);
            nextCharacter = nextCharacter();
        }
        return new Token(Token.Type.IDENT, ident.toString());
    }
}

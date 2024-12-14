package ch.mobi.arithmetic;

public class Parser {

    private final Scanner scanner;

    private Token lookahead;
    private Token current;

    public Parser(Scanner scanner) {

        this.scanner = scanner;
    }

    private void scan() {

        current = lookahead;
        lookahead = scanner.next();
    }

    private void check(Token.Type type) {
        if (lookahead.type() == type)
            scan();
        // TODO: Throw error
    }

    public double parse() {

        scan();
        double result = expr();
        check(Token.Type.EOF);
        return result;
    }

    private double expr() {

        var factor = 1.d;
        if (lookahead.type() == Token.Type.PLUS) {
            check(Token.Type.PLUS);
        } else if (lookahead.type() == Token.Type.MINUS) {
            check(Token.Type.MINUS);
            factor = -1.d;
        } else {
            // TODO: Handle error
        }
        var result = factor * term();
        while (lookahead.type() == Token.Type.PLUS || lookahead.type() == Token.Type.MINUS) {

            var add = true;
            if (lookahead.type() == Token.Type.PLUS) {
                check(Token.Type.PLUS);
            } else if (lookahead.type() == Token.Type.MINUS) {
                check(Token.Type.MINUS);
                add = false;
            } else {
                // TODO: Handle error
            }
            var termResult = term();
            result = add ? result + termResult : result - termResult;
        }
        return result;
    }

    private double term() {

        double result = factor();
        while (lookahead.type() == Token.Type.MULTIPLY || lookahead.type() == Token.Type.DIVIDE) {
            var multiply = true;
            if (lookahead.type() == Token.Type.MULTIPLY) {
                check(Token.Type.MULTIPLY);
            } else if (lookahead.type() == Token.Type.DIVIDE) {
                check(Token.Type.DIVIDE);
                multiply = false;
            } else {
                // TODO: Handle error
            }
            var factorResult = factor();
            result = multiply ? result * factorResult : result / factorResult;
        }
        return result;
    }

    private double factor() {

        if (lookahead.type() == Token.Type.NUMBER) {
            check(Token.Type.NUMBER);
            return current.value();
        } else if (lookahead.type() == Token.Type.LEFT_BRACKET) {
            check(Token.Type.LEFT_BRACKET);
            var result = expr();
            check(Token.Type.RIGHT_BRACKET);
            return result;
        } else {
            // TODO: Handle error
        }
        return 0.d;
    }
}

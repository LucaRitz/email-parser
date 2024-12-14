package ch.mobi.email.solution;

import java.util.ArrayList;

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
        if (lookahead.type() == type) {
            scan();
        } else {
            throw new InvalidEmailException("expected token: " + type + ", found: " + lookahead.type());
        }
    }

    public Email parse() throws InvalidEmailException {

        scan();
        var email = email();
        check(Token.Type.EOF);
        return email;
    }

    private Email email() {

        var address = address();
        check(Token.Type.AT);
        var domain = domain();
        return new Email(address, domain);
    }

    private Email.Address address() {

        var parts = new ArrayList<String>();
        parts.add(name());
        if (Token.Type.PERIOD.equals(lookahead.type())) {
            check(Token.Type.PERIOD);
            parts.add(name());
        }
        return new Email.Address(parts);
    }

    private String name() {

        check(Token.Type.IDENT);
        return current.value();
    }

    private Email.Domain domain() {

        var parts = new ArrayList<String>();
        parts.add(name());
        check(Token.Type.PERIOD);
        parts.add(name());
        while(Token.Type.PERIOD.equals(lookahead.type())) {
            check(Token.Type.PERIOD);
            parts.add(name());
        }
        return new Email.Domain(parts);
    }
}

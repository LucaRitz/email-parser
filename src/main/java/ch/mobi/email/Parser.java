package ch.mobi.email;

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

    public Email parse() {

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

    private String address() {

        var name = name();
        if (Token.Type.PERIOD.equals(lookahead.type())) {
            check(Token.Type.PERIOD);
            name += "." + name();
        }
        return name;
    }

    private String name() {

        check(Token.Type.IDENT);
        return current.value();
    }

    private String domain() {

        var name = name();
        check(Token.Type.PERIOD);
        name += "." + name();
        while(Token.Type.PERIOD.equals(lookahead.type())) {
            check(Token.Type.PERIOD);
            name += "." + name();
        }
        return name;
    }
}

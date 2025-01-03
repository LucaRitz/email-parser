package ch.mobi.email.solution;

public record Token(Type type, String value) {

    public Token(Type type) {
        this(type, "");
    }

    public enum Type {
        IDENT,
        EOF,
        AT,
        PERIOD,
        UNKNOWN
    }
}

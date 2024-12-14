package ch.mobi.arithmetic;

public record Token(Type type, double value) {

    public Token(Type type) {
        this(type, -1);
    }

    public enum Type {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        NUMBER,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        EOF,
        UNKNOWN
    }
}

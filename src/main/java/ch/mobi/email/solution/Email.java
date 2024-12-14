package ch.mobi.email.solution;

import java.util.List;

public record Email(Address address, Domain domain) {

    public record Address(List<String> parts) {}

    public record Domain(List<String> parts) {}
}

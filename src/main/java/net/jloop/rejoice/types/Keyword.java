package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.Objects;
import java.util.Optional;

public final class Keyword implements Atom {

    private final String namespace;
    private final String name;

    private Keyword(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public static Keyword of(String keyword) {
        keyword = keyword.substring(1);
        int i = keyword.indexOf('/');
        if (i == -1 || keyword.equals("/")) {
            return new Keyword(null, keyword);
        } else {
            return new Keyword(keyword.substring(0, i), keyword.substring(i + 1));
        }
    }

    public static Keyword of(String namespace, String name) {
        return new Keyword(namespace, name);
    }

    public Optional<String> namespace() {
        return Optional.ofNullable(namespace);
    }

    public String name() {
        return name;
    }

    @Override
    public String print() {
        return namespace == null ? name : namespace + "/" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(namespace, keyword.namespace) && name.equals(keyword.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name);
    }
}

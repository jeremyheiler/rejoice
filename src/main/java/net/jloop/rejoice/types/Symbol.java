package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.Objects;
import java.util.Optional;

public final class Symbol implements Atom {

    private final String namespace;
    private final String name;

    private Symbol(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public static Symbol of(String namespace, String name) {
        return new Symbol(namespace, name);
    }

    public static Symbol of(String name) {
        int i = name.indexOf('/');
        if (i == -1 || name.equals("/")) {
            return new Symbol(null, name);
        } else {
            return new Symbol(name.substring(0, i), name.substring(i + 1));
        }
    }

    public Optional<String> namespace() {
        return Optional.ofNullable(namespace);
    }

    public String name() {
        return name;
    }

    @Override
    public String print() {
        if (namespace == null) {
            return name;
        } else {
            return namespace + "/" + name;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(namespace, symbol.namespace) && name.equals(symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name);
    }
}

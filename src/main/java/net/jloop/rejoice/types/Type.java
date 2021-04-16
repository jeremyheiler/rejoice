package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.Objects;
import java.util.Optional;

public final class Type implements Atom {

    private final String path;
    private final String name;

    private Type(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public static Type of(String name) {
        int i = name.indexOf('/');
        if (i == -1 || name.equals("/")) {
            return new Type(null, name);
        } else {
            return new Type(name.substring(0, i), name.substring(i + 1));
        }
    }

    public Optional<String> path() {
        return Optional.ofNullable(path);
    }

    public String name() {
        return name;
    }

    @Override
    public String print() {
        return "^" + value();
    }

    @Override
    public String value() {
        return path == null ? name : path + "/" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(path, type.path) && name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, name);
    }
}

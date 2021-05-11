package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.Objects;

public final class Type implements Atom {

    private final String name;

    private Type(String name) {
        this.name = name;
    }

    public static Type of(String name) {
        return new Type(name);
    }
    public String name() {
        return name;
    }

    @Override
    public String print() {
        return "^" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

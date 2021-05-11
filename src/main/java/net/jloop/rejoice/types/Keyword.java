package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.Objects;

public final class Keyword implements Atom {

    private final String name;

    private Keyword(String name) {
        this.name = name;
    }

    public static Keyword of(String keyword) {
        return new Keyword(keyword);
    }

    public String name() {
        return name;
    }

    @Override
    public String print() {
        return ":" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return name.equals(keyword.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

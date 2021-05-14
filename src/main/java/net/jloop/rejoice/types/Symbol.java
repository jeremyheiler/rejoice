package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.Objects;

public final class Symbol implements Atom {

    private final String name;

    private Symbol(String name) {
        this.name = name;
    }

    public static Symbol of(String symbol) {
        return new Symbol(symbol);
    }

    public String name() {
        return name;
    }

    @Override
    public Type type() {
        return Type.Symbol;
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return name.equals(symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Quotable;

import java.util.Objects;

public final class Symbol implements Atom, Quotable {

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
    public Symbol symbol() {
        return this;
    }

    @Override
    public Atom quote() {
        return new Quote(this);
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

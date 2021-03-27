package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.HashMap;
import java.util.Objects;

public final class Symbol implements Atom {

    private static final HashMap<String, Symbol> interns = new HashMap<>();

    private final String name;

    private Symbol(String name) {
        this.name = name;
    }

    public static Symbol of(String name) {
        Symbol symbol;
        if (interns.containsKey(name)) {
            symbol = interns.get(name);
        } else {
            symbol = new Symbol(name);
            interns.put(name, symbol);
        }
        return symbol;
    }

    public String getName() {
        return name;
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
        return Objects.equals(name, symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

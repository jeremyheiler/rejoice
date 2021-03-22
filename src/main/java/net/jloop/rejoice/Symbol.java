package net.jloop.rejoice;

import java.util.Objects;

public class Symbol implements Atom {

    private final String value;

    private Symbol(String value) {
        this.value = value;
    }

    // TODO(jeremy): Intern symbols
    public static Symbol of(String symbol) {
        return new Symbol(symbol);
    }

    public String getName() {
        return value;
    }

    @Override
    public String print() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(value, symbol.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

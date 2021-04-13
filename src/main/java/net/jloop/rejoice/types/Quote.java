package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public class Quote implements Atom {

    private final Symbol symbol;

    public Quote(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol get() {
        return symbol;
    }

    @Override
    public String print() {
        return value();
    }

    @Override
    public String value() {
        return "'" + symbol.print();
    }
}

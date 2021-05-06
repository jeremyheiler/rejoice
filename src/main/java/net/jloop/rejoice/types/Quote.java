package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Quotable;

public final class Quote implements Atom, Quotable {

    private final Quotable quotable;

    public Quote(Quotable quotable) {
        this.quotable = quotable;
    }

    @Override
    public Symbol symbol() {
        return quotable.symbol();
    }

    @Override
    public Atom quote() {
        return new Quote(this);
    }

    @Override
    public Atom unquote() {
        return quotable;
    }

    @Override
    public String print() {
        return "'" + quotable.print();
    }
}

package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Quotable;

public final class Quote implements Atom, Quotable {

    private final Quotable quotable;

    public Quote(Quotable quotable) {
        this.quotable = quotable;
    }

    @Override
    public Type type() {
        return Type.Quote;
    }

    @Override
    public Symbol symbol() {
        return quotable.symbol();
    }

    @Override
    public Quote quote() {
        return new Quote(this);
    }

    public Quotable unquote() {
        return quotable;
    }

    @Override
    public String print() {
        return "'" + quotable.print();
    }
}

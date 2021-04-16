package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Module;
import net.jloop.rejoice.Quotable;

public final class Quote implements Atom, Quotable {

    private final Quotable quotable;

    public Quote(Quotable quotable) {
        this.quotable = quotable;
    }

    @Override
    public String name() {
        return quotable.name();
    }

    @Override
    public Atom quote() {
        return new Quote(this);
    }

    @Override
    public Atom unquote(Module module) {
        return quotable;
    }

    @Override
    public String print() {
        return null;
    }

    @Override
    public String value() {
        return null;
    }
}

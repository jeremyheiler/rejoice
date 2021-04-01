package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public class Quote implements Atom {

    private final Atom quoted;

    public Quote(Atom quoted) {
        this.quoted = quoted;
    }

    public Atom get() {
        return quoted;
    }

    @Override
    public String print() {
        return "'" + quoted.print();
    }
}

package net.jloop.rejoice.literals;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Literal;

public class Quote implements Literal {

    private final Atom atom;

    public Quote(Atom atom) {
        this.atom = atom;
    }

    public <V extends Atom> V unquote(Class<V> type) {
        if (!type.isInstance(atom)) {
            throw new RuntimeException("Expecting type " + type + " but found " + atom.getClass());
        } else {
            return type.cast(atom);
        }
    }
}

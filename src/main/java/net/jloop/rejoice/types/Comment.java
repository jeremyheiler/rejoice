package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;

import java.util.Iterator;
import java.util.List;

public final class Comment implements Atom {

    private final String value;

    public Comment(String value) {
        this.value = value;
    }

    @Override
    public Iterator<Atom> rewrite(Context context, List<Atom> output, Iterator<Atom> input) {
        return input;
    }

    @Override
    public String print() {
        return "#" + value;
    }
}

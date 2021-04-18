package net.jloop.rejoice;

import java.util.Iterator;
import java.util.List;

public interface Atom extends Value {

    @Override
    default Atom quote() {
        return this;
    }

    @Override
    default Atom unquote(Module module) {
        return this;
    }

    default Iterator<Atom> rewrite(Context context, List<Atom> output, Iterator<Atom> input) {
        output.add(this);
        return input;
    }
}

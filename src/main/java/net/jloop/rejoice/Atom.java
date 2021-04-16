package net.jloop.rejoice;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Atom extends Value {

    @Override
    default Atom quote() {
        return this;
    }

    @Override
    default Atom unquote(Module module) {
        return this;
    }

    default Iterator<Atom> rewrite(Map<String, Macro> macros, List<Atom> output, Iterator<Atom> input) {
        output.add(this);
        return input;
    }
}

package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class Rewriter {

    private final Map<String, Macro> macros;

    public Rewriter(Map<String, Macro> macros) {
        this.macros = macros;
    }

    public Iterable<Atom> rewrite(Iterator<Atom> input) {
        ArrayList<Atom> output = new ArrayList<>();
        while (input.hasNext()) {
            input = input.next().rewrite(macros, output, input);
        }
        return output;
    }
}

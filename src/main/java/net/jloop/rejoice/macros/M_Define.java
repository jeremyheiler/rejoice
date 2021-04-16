package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class M_Define implements Macro {

    @Override
    public Iterable<Atom> rewrite(Map<String, Macro> macros, Iterator<Atom> input) {
        Symbol name = Macro.match(input, Symbol.class);
        Macro.match(input, Symbol.of(":"));
        ArrayList<Atom> rewritten = new ArrayList<>();
        rewritten.add(Symbol.of("("));
        Macro.collectInto(macros, rewritten, input, Symbol.of(";"));
        rewritten.add(Symbol.of(")"));
        rewritten.add(name.quote());
        rewritten.add(Symbol.of("define!"));
        return rewritten;
    }
}

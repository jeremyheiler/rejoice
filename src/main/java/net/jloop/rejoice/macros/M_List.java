package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class M_List implements Macro {

    @Override
    public Iterable<Atom> rewrite(Map<String, Macro> macros, Iterator<Atom> input) {
        List<Atom> rewritten = new ArrayList<>();
        for (Atom atom : Macro.collect(macros, input, Symbol.of(")"))) {
            rewritten.add(atom.quote());
        }
        rewritten.add(new Int64(rewritten.size()));
        rewritten.add(Symbol.of("list"));
        return rewritten;
    }
}

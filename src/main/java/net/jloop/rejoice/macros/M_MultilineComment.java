package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.types.Symbol;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class M_MultilineComment implements Macro {

    @Override
    public Iterable<Atom> rewrite(Map<String, Macro> macros, Iterator<Atom> input) {
        while (true) {
            if (input.next().equals(Symbol.of("*/"))) {
                break;
            }
        }
        return Collections.emptyList();
    }
}

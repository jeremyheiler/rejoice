package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.types.Symbol;

import java.util.Collections;
import java.util.Iterator;

public class M_MultilineComment implements Macro {

    private final Symbol dispatcher;
    private final Symbol terminator;

    public M_MultilineComment(Symbol dispatcher, Symbol terminator) {
        this.dispatcher = dispatcher;
        this.terminator = terminator;
    }

    @Override
    public Iterable<Atom> rewrite(Rewriter rewriter, Iterator<Atom> input) {
        while (true) {
            if (input.next().equals(terminator)) {
                break;
            }
        }
        return Collections.emptyList();
    }

    public Symbol dispatcher() {
        return dispatcher;
    }
}

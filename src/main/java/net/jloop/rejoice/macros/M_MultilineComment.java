package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;

public class M_MultilineComment implements Macro {

    private final Symbol terminator;

    public M_MultilineComment(Symbol terminator) {
        this.terminator = terminator;
    }

    @Override
    public Iterator<Atom> rewrite(Rewriter rewriter, Iterator<Atom> iterator) {
        rewriter.collect(iterator, terminator);
        return iterator;
    }
}

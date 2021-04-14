package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;

public final class M_Define implements Macro {

    @Override
    public Iterable<Atom> rewrite(Rewriter rewriter, Iterator<Atom> input) {
        Symbol name = rewriter.match(input, Symbol.class);
        rewriter.match(input, Symbol.of(":"));
        ArrayList<Atom> output = new ArrayList<>();
        output.add(Symbol.of("("));
        rewriter.collectInto(output, input, Symbol.of(";"), false);
        output.add(Symbol.of(")"));
        output.add(name.quote());
        output.add(Symbol.of("define!"));
        return output;
    }
}

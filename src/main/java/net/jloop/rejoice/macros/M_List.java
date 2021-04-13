package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;
import java.util.List;

public final class M_List implements Macro {

    @Override
    public Iterable<Atom> rewrite(Rewriter rewriter, Iterator<Atom> input) {
        List<Atom> output = rewriter.collect(input, Symbol.of("]"), true);
        output.add(new Int64(output.size()));
        output.add(Symbol.of("list"));
        return output;
    }
}

package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;

public final class M_Define implements Macro {

    private final Symbol op_define;
    private final Symbol separator;
    private final Symbol terminator;
    private final Symbol listStart;
    private final Symbol listEnd;

    public M_Define(Symbol op_define, Symbol separator, Symbol terminator, Symbol listStart, Symbol listEnd) {
        this.op_define = op_define;
        this.separator = separator;
        this.terminator = terminator;
        this.listStart = listStart;
        this.listEnd = listEnd;
    }

    @Override
    public Iterable<Atom> rewrite(Rewriter rewriter, Iterator<Atom> input) {
        Symbol name = rewriter.match(input, Symbol.class);
        rewriter.match(input, separator);
        ArrayList<Atom> output = new ArrayList<>();
        output.add(listStart);
        rewriter.collectInto(output, input, terminator, false);
        output.add(listEnd);
        output.add(new Quote(name));
        output.add(op_define);
        return output;
    }
}

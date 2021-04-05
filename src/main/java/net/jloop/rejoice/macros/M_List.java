package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;
import java.util.List;

public final class M_List implements Macro {

    private final Symbol terminator;
    private final Symbol op_list;

    public M_List(Symbol terminator, Symbol operator) {
        this.terminator = terminator;
        this.op_list = operator;
    }

    @Override
    public List<Atom> rewrite(Rewriter rewriter, Iterator<Atom> iterator) {
        List<Atom> atoms = rewriter.collect(iterator, terminator);
        atoms.add(new Int64(atoms.size()));
        atoms.add(op_list);
        return atoms;
    }
}

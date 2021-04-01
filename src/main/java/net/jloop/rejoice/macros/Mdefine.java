package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.PushIterator;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;
import java.util.List;

public final class Mdefine implements Macro {

    private final Symbol op_define;
    private final Symbol op_list;
    private final Symbol separator;
    private final Symbol terminator;

    public Mdefine(Symbol op_define, Symbol op_list, Symbol separator, Symbol terminator) {
        this.op_define = op_define;
        this.op_list = op_list;
        this.separator = separator;
        this.terminator = terminator;
    }

    @Override
    public Iterator<Atom> rewrite(Rewriter rewriter, Iterator<Atom> iterator) {

        Symbol name = null;
        if (iterator.hasNext()) {
            Atom atom = iterator.next();
            if (atom instanceof Symbol) {
                name = (Symbol) atom;
            } else {
                throw new RuntimeError("MACRO", "Expecting a Symbol, but found " + atom.getClass().getSimpleName());
            }
        }

        if (iterator.hasNext()) {
            Atom atom = iterator.next();
            if (!atom.equals(separator)) {
                if (atom instanceof Symbol) {
                    throw new RuntimeError("MACRO", "Expecting Symbol '" + separator.getName() + "' , but found Symbol '" + ((Symbol) atom).getName() + "'");
                } else {
                    throw new RuntimeError("MACRO", "Expecting Symbol '" + separator.getName() + "' , but found " + atom.getClass().getSimpleName());
                }
            }
        }

        List<Atom> atoms = rewriter.collect(iterator, terminator);
        return new PushIterator<>(iterator)
                .push(op_define)
                .push(new Quote(name))
                .push(op_list)
                .push(new Int64(atoms.size()))
                .push(atoms);
    }
}

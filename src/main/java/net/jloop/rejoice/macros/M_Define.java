package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final class M_Define implements Macro {

    private final Symbol op_define;
    private final Symbol op_list;
    private final Symbol separator;
    private final Symbol terminator;

    public M_Define(Symbol op_define, Symbol op_list, Symbol separator, Symbol terminator) {
        this.op_define = op_define;
        this.op_list = op_list;
        this.separator = separator;
        this.terminator = terminator;
    }

    @Override
    public List<Atom> rewrite(Rewriter rewriter, Iterator<Atom> iterator) {
        return rewrite(rewriter, iterator, null, null);
    }

    // The extra parameters are used in Joy mode to handle custom termination logic in the LIBRA macro.
    public List<Atom> rewrite(Rewriter rewriter, Iterator<Atom> iterator, Symbol terminator, Consumer<Symbol> termination) {
        Symbol name = null;
        if (iterator.hasNext()) {
            Atom atom = iterator.next();
            if (atom instanceof Symbol) {
                name = (Symbol) atom;
            } else {
                throw new RuntimeError("MACRO", "Expecting a Symbol, but found " + atom.getClass().getSimpleName() + " - " + atom.print());
            }
        }
        if (iterator.hasNext()) {
            Atom atom = iterator.next();
            if (!atom.equals(separator)) {
                if (atom instanceof Symbol) {
                    throw new RuntimeError("MACRO", "Expecting Symbol '" + separator.getName() + "' , but found Symbol '" + ((Symbol) atom).getName() + "'");
                } else {
                    throw new RuntimeError("MACRO", "Expecting Symbol '" + separator.getName() + "' , but found " + atom.getClass().getSimpleName() + " - " + atom.print());
                }
            }
        }
        List<Atom> atoms = rewriter.collect(iterator, terminator == null ? Set.of(this.terminator) : Set.of(this.terminator, terminator), termination);
        atoms.add(new Int64(atoms.size()));
        atoms.add(op_list);
        atoms.add(new Quote(name));
        atoms.add(op_define);
        return atoms;
    }
}

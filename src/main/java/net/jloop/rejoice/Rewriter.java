package net.jloop.rejoice;

import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class Rewriter {

    private final Map<Symbol, Macro> macros;

    public Rewriter(Map<Symbol, Macro> macros) {
        this.macros = macros;
    }

    public Iterable<Atom> rewrite(Iterable<Atom> iterable) {
        Iterator<Atom> iterator = iterable.iterator();
        ArrayList<Atom> atoms = new ArrayList<>();
        while (iterator.hasNext()) {
            Atom atom = iterator.next();
            if ((atom instanceof Symbol) && macros.containsKey(atom)) {
                atoms.addAll(macros.get(atom).rewrite(this, iterator));
            } else {
                atoms.add(atom);
            }
        }
        return atoms;
    }

    // TODO(jeremy): Refactor these methods into a CollectBuilder

    public List<Atom> collect(Iterator<Atom> iterator, Symbol terminator) {
        return collect(iterator, macros.keySet(), Set.of(terminator), null);
    }

    public List<Atom> collect(Iterator<Atom> iterator, Set<Symbol> terminators, Consumer<Symbol> termination) {
        return collect(iterator, macros.keySet(), terminators, termination);
    }

    public List<Atom> collect(Iterator<Atom> iterator, Set<Symbol> macroNames, Set<Symbol> terminators, Consumer<Symbol> termination) {
        ArrayList<Atom> atoms = new ArrayList<>();
        while (iterator.hasNext()) {
            Atom atom = iterator.next();
            if (atom instanceof Symbol) {
                if (terminators.contains(atom)) {
                    if (termination != null) {
                        termination.accept((Symbol) atom);
                    }
                    return atoms;
                } else if (macroNames.contains(atom)) {
                    iterator = new ConcatIterator<>(macros.get(atom).rewrite(this, iterator), iterator);
                } else {
                    atoms.add(new Quote(atom));
                }
            } else {
                atoms.add(atom);
            }
        }
        throw new RuntimeError("MACRO", "Unexpected EOF");
    }
}

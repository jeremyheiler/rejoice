package net.jloop.rejoice;

import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            if ((atom instanceof Symbol) && isMacro((Symbol) atom)) {
                iterator = rewriteMacro((Symbol) atom, iterator);
            } else {
                atoms.add(atom);
            }
        }
        return atoms;
    }

    public List<Atom> collect(Iterator<Atom> iterator, Symbol terminator) {
        ArrayList<Atom> atoms = new ArrayList<>();
        while (iterator.hasNext()) {
            Atom atom = iterator.next();
            if (atom instanceof Symbol) {
                if (atom.equals(terminator)) {
                    return atoms;
                } else if (isMacro((Symbol) atom)) {
                    iterator = rewriteMacro((Symbol) atom, iterator);
                } else {
                    atoms.add(new Quote(atom));
                }
            } else {
                atoms.add(atom);
            }
        }
        throw new RuntimeError("MACRO", "Unexpected EOF");
    }

    private boolean isMacro(Symbol name) {
        return macros.containsKey(name);
    }

    private Iterator<Atom> rewriteMacro(Symbol name, Iterator<Atom> iterator) {
        return macros.get(name).rewrite(this, iterator);
    }
}

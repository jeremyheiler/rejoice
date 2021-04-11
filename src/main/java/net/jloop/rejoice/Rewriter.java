package net.jloop.rejoice;

import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Rewriter {

    private final Map<Symbol, Macro> macros;

    public Rewriter(Map<Symbol, Macro> macros) {
        this.macros = macros;
    }

    public Iterable<Atom> rewrite(Iterator<Atom> input) {
        ArrayList<Atom> output = new ArrayList<>();
        while (input.hasNext()) {
            Atom next = input.next();
            if ((next instanceof Symbol) && macros.containsKey(next)) {
                input = new ConcatIterator<>(macros.get(next).rewrite(this, input), input);
            } else {
                output.add(next);
            }
        }
        return output;
    }

    public List<Atom> collect(Iterator<Atom> input, Symbol terminator) {
        return collect(input, Set.of(terminator), false);
    }

    public List<Atom> collect(Iterator<Atom> input, Set<Symbol> terminators) {
        return collect(input, terminators, false);
    }

    public List<Atom> collect(Iterator<Atom> input, Symbol terminator, boolean quote) {
        return collect(input, Set.of(terminator), quote);
    }

    public List<Atom> collect(Iterator<Atom> input, Set<Symbol> terminators, boolean quote) {
        List<Atom> output = new ArrayList<>();
        collectInto(output, input, terminators, quote);
        return output;
    }

    public void collectInto(List<Atom> output, Iterator<Atom> input, Symbol terminator, boolean quote) {
        collectInto(output, input, Set.of(terminator), quote);
    }

    public Symbol collectInto(List<Atom> output, Iterator<Atom> input, Set<Symbol> terminators, boolean quote) {
        while (true) {
            if (input.hasNext()) {
                Atom next = input.next();
                if (next instanceof Symbol) {
                    if (terminators.contains(next)) {
                        return (Symbol) next;
                    } else if (macros.containsKey(next)) {
                        for (Atom atom : macros.get(next).rewrite(this, input)) {
                            output.add(atom instanceof Symbol && quote ? new Quote((Symbol) atom) : atom);
                        }
                    } else if (quote) {
                        output.add(new Quote((Symbol) next));
                    } else {
                        output.add(next);
                    }
                } else {
                    output.add(next);
                }
            } else {
                throw new RuntimeError("MACRO", "Unexpected EOF");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Atom> T match(Iterator<Atom> input, Class<T> type) {
        Atom next = input.next();
        if (type.isInstance(next)) {
            return (T) next;
        } else {
            throw new RuntimeError("MACRO", "Expecting a Symbol, but found " + next.getClass().getSimpleName() + " '" + next.print() + "'");
        }
    }

    public void match(Iterator<Atom> input, Symbol symbol) {
        Atom next = input.next();
        if (!next.equals(symbol)) {
            throw new RuntimeError("MACRO", "Expecting Symbol '" + symbol.print() + "' , but found " + next.getClass().getSimpleName() + " '" + next.print() + "'");
        }
    }
}
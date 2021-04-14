package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Rewriter {

    private final Map<String, Macro> macros;

    public Rewriter(Map<String, Macro> macros) {
        this.macros = macros;
    }

    public Iterable<Atom> rewrite(Iterator<Atom> input) {
        ArrayList<Atom> output = new ArrayList<>();
        while (input.hasNext()) {
            Atom next = input.next();
            if ((next instanceof Symbol) && macros.containsKey(((Symbol) next).name())) {
                input = new ConcatIterator<>(macros.get(((Symbol) next).name()).rewrite(this, input), input);
            } else {
                output.add(next);
            }
        }
        return output;
    }

    public List<Atom> collect(Iterator<Atom> input, Symbol terminator, boolean quote) {
        List<Atom> output = new ArrayList<>();
        collectInto(output, input, terminator, quote);
        return output;
    }

    // TODO: Maybe return the number of atoms collected?
    public void collectInto(List<Atom> output, Iterator<Atom> input, Symbol terminator, boolean quote) {
        while (true) {
            if (input.hasNext()) {
                Atom next = input.next();
                if (next instanceof Symbol) {
                    Symbol symbol = (Symbol) next;
                    if (symbol.isQuoted()) {
                        if (quote) {
                            output.add(symbol.quote());
                        } else {
                            output.add(symbol);
                        }
                    } else if (symbol.equals(terminator)) {
                        break;
                    } else if (macros.containsKey(symbol.name())) {
                        for (Atom atom : macros.get(symbol.name()).rewrite(this, input)) {
                            output.add(atom instanceof Symbol && quote ? ((Symbol) atom).quote() : atom);
                        }
                    } else if (quote) {
                        output.add(symbol.quote());
                    } else {
                        output.add(symbol);
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

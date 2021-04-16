package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface Macro {

    Iterable<Atom> rewrite(Map<String, Macro> macros,  Iterator<Atom> input);

    static List<Atom> collect(Map<String, Macro> macros, Iterator<Atom> input, Symbol terminator) {
        List<Atom> output = new ArrayList<>();
        collectInto(macros, output, input, terminator);
        return output;
    }

    // TODO: Maybe return the number of atoms collected?
    static void collectInto(Map<String, Macro> macros, List<Atom> output, Iterator<Atom> input, Symbol terminator) {
        while (true) {
            if (input.hasNext()) {
                Atom next = input.next();
                if (next.equals(terminator)) {
                    break;
                } else {
                    next.rewrite(macros, output, input);
                }
            } else {
                throw new RuntimeError("MACRO", "Unexpected EOF");
            }
        }
    }

    @SuppressWarnings("unchecked")
    static <T extends Atom> T match(Iterator<Atom> input, Class<T> type) {
        Atom next = input.next();
        if (type.isInstance(next)) {
            return (T) next;
        } else {
            throw new RuntimeError("MACRO", "Expecting a Symbol, but found " + next.getClass().getSimpleName() + " '" + next.print() + "'");
        }
    }

    static void match(Iterator<Atom> input, Symbol symbol) {
        Atom next = input.next();
        if (!next.equals(symbol)) {
            throw new RuntimeError("MACRO", "Expecting Symbol '" + symbol.print() + "' , but found " + next.getClass().getSimpleName() + " '" + next.print() + "'");
        }
    }
}

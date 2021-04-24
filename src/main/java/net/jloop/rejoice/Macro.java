package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@FunctionalInterface
public interface Macro {

    Iterator<Atom> rewrite(Context context, Iterator<Atom> input);

    static List<Atom> collect(Context context, Iterator<Atom> input, Symbol terminator) {
        List<Atom> output = new ArrayList<>();
        while (input.hasNext()) {
            Atom next = input.next();
            if (!next.isIgnorable()) {
                if (next.equals(terminator)) {
                    break;
                } else {
                    input = next.rewrite(context, output, input);
                }
            }
        }
        return output;
    }

    @SuppressWarnings("unchecked")
    static <T extends Atom> T match(Iterator<Atom> input, Class<T> type) {
        while (true) {
            if (input.hasNext()) {
                Atom next = input.next();
                if (!next.isIgnorable()) {
                    if (type.isInstance(next)) {
                        return (T) next;
                    } else {
                        throw new RuntimeError("MACRO", "Expecting to match ^" + type.getSimpleName().toLowerCase() + " , but found ^" + next.getClass().getSimpleName().toLowerCase() + " with value '" + next.print() + "'");
                    }
                }
            } else {
                throw new RuntimeError("MACRO", "Unexpected EOF when attempting to match ^" + type.getSimpleName().toLowerCase() );
            }
        }
    }

    static void match(Iterator<Atom> input, Symbol symbol) {
        while (true) {
            if (input.hasNext()) {
                Atom next = input.next();
                if (!next.isIgnorable()) {
                    if (next.equals(symbol)) {
                        break;
                    } else {
                        throw new RuntimeError("MACRO", "Expecting to match ^symbol '" + symbol.print() + "' , but found ^" + next.getClass().getSimpleName().toLowerCase() + " with value '" + next.print() + "'");
                    }
                }
            } else {
                throw new RuntimeError("MACRO", "Unexpected EOF when attempting to match ^symbol '" + symbol.print() + "'");
            }
        }
    }
}

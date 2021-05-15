package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MacroHelper {

    public static List<Value> collect(Env env, Iterator<Value> input, Symbol terminator) {
        List<Value> output = new ArrayList<>();
        while (true) {
            if (!input.hasNext()) {
                throw new RuntimeError("MACRO", "Input stream ended before finding the terminating symbol '" + terminator.print() + "'");
            }
            Value next = input.next();
            if (next instanceof Symbol) {
                if (next.equals(terminator)) {
                    return output;
                }
                Function function = env.lookup((Symbol) next);
                if (function instanceof Macro) {
                    env.trace().push((Symbol) next);
                    Iterator<Value> values = ((Macro) function).call(env, input);
                    while (values.hasNext()) {
                        output.add(values.next());
                    }
                    env.trace().pop();
                } else {
                    output.add(next);
                }
            } else {
                output.add(next);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Value> T match(Iterator<Value> input, Type type) {
        if (!input.hasNext()) {
            throw new RuntimeError("MACRO", "Unexpected EOF when attempting to match " + type.print());
        }
        Value next = input.next();
        if (type == next.type()) {
            return (T) next;
        } else {
            throw new RuntimeError("MACRO", "Expecting to match " + type.print() + ", but found " + next.type().print() + " with value '" + next.print() + "'");
        }
    }

    public static void match(Iterator<Value> input, Symbol symbol) {
        if (!input.hasNext()) {
            throw new RuntimeError("MACRO", "Unexpected EOF when attempting to match ^symbol '" + symbol.print() + "'");
        }
        Value next = input.next();
        if (!next.equals(symbol)) {
            throw new RuntimeError("MACRO", "Expecting to match symbol '" + symbol.print() + "' , but found " + next.type().print() + " with value '" + next.print() + "'");
        }
    }
}

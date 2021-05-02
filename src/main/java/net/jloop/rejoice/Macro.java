package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;
import java.util.List;

@FunctionalInterface
public interface Macro extends Invocable {

    Iterator<Value> rewrite(Env env, Iterator<Value> input);

    @Override
    default Stack invoke(Env env, Stack stack, Iterator<Value> input) {
        return new Interpreter().interpret(env, stack, rewrite(env, input));
    }

    static void collect(Env env, Iterator<Value> input, List<Value> collection, Symbol terminator) {
        while (true) {
            if (!input.hasNext()) {
                throw new RuntimeError("MACRO", "Input stream ended before finding the terminating symbol '" + terminator.print() + "'");
            }
            Value next = input.next();
            if (next.equals(terminator)) {
                break;
            } else {
                next.collect(env, input, collection);
            }
        }
    }

    @SuppressWarnings("unchecked")
    static <T extends Value> T match(Iterator<Value> input, Class<T> type) {
        if (!input.hasNext()) {
            throw new RuntimeError("MACRO", "Unexpected EOF when attempting to match ^" + type.getSimpleName().toLowerCase());
        }
        Value next = input.next();
        if (type.isInstance(next)) {
            return (T) next;
        } else {
            throw new RuntimeError("MACRO", "Expecting to match ^" + type.getSimpleName().toLowerCase() + " , but found ^" + next.getClass().getSimpleName().toLowerCase() + " with value '" + next.print() + "'");
        }
    }

    static void match(Iterator<Value> input, Symbol symbol) {
        if (!input.hasNext()) {
            throw new RuntimeError("MACRO", "Unexpected EOF when attempting to match ^symbol '" + symbol.print() + "'");
        }
        Value next = input.next();
        if (!next.equals(symbol)) {
            throw new RuntimeError("MACRO", "Expecting to match ^symbol '" + symbol.print() + "' , but found ^" + next.getClass().getSimpleName().toLowerCase() + " with value '" + next.print() + "'");
        }
    }
}

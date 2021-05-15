package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

// x [... [x xs] ...] -> [xs]
// Given a list of lists, return the rest of the first list where the type of the first element of that list matches the type of x.
// If none match, return the last list. If there's only one sub list, return the whole sub list.

public final class Oopcase implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Quote quote = data.consume(Quote.class);
        Atom atom = data.consume(Atom.class);
        if (quote.length() == 0) {
            throw new RuntimeError("INTERPRET", "Operator 'opcase': list cannot be empty");
        }
        if (quote.length() == 1) {
            return data.push(quote.first());
        }
        int count = 0;
        for (Value value : quote) {
            if (value instanceof Quote) {
                Quote l = (Quote) value;
                if (++count == quote.length()) { // last list
                    return data.push(l);
                }
                if (l.length() == 0) {
                    throw new RuntimeException("LIST cannot be empty");
                }
                if (l.first().getClass() == atom.getClass()) {
                    return data.push(l.rest());
                }
            } else {
                throw new RuntimeException("Expecting a LIST");
            }
        }
        return data;
    }
}

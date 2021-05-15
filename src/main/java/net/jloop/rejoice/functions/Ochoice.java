package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

// b t f -> r
// If b is true, leave t on the stack. If b is false, leave f on the stack.

public final class Ochoice implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Atom f = data.consume(Atom.class);
        Atom t = data.consume(Atom.class);
        Bool b = data.consume(Bool.class);
        Atom r = b == Bool.True ? t : f;
        return data.push(r);
    }
}

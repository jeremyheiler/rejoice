package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

// x y -> b

public final class Oequal_Q_ implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Atom y = data.consume(Atom.class);
        Atom x = data.consume(Atom.class);
        Bool b = Bool.of(x.equals(y));
        return data.push(b);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Stack;

// x y -> b

public final class Oequal_Q_ implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Bool b = Bool.of(x.equals(y));
        return stack.push(b);
    }
}

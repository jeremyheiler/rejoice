package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// x y [p] -> ... x y

public final class Cdipd implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return p.evaluate(env, stack).push(x).push(y);
    }
}

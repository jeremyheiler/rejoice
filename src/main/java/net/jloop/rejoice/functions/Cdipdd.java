package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// x y z [p] -> ... x y z

public final class Cdipdd implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote p = stack.consume(Quote.class);
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return p.call(env, stack).push(x).push(y).push(z);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// x y z [p] -> ... x y z

public final class Cdipdd implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return p.invoke(env, stack).push(x).push(y).push(z);
    }
}

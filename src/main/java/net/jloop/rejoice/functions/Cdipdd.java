package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.List;

// x y z [p] -> ... x y z

public final class Cdipdd implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return p.interpret(context, stack).push(x).push(y).push(z);
    }
}

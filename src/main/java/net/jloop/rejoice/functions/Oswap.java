package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

// x y -> y x

public final class Oswap implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(x);
    }
}

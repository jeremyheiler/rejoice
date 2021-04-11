package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

// x y -> x x y

public final class Odupd implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.peek(Atom.class);
        return stack.push(x).push(y);
    }
}

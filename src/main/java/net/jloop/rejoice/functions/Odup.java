package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

// x -> x x

public final class Odup implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Atom x = stack.peek(Atom.class);
        return stack.push(x);
    }
}

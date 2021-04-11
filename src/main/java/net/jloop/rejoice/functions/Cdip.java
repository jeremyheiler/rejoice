package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x [p] -> ... x

public final class Cdip implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        return context.interpreter().interpret(stack, context, p).push(x);
    }
}

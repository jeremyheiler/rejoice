package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.List;

// x [p] -> r
// Replace x with the result of evaluating the quote p with a stack containing only x.

public final class Capp1 implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        Atom r = p.invoke(context, new Stack().push(x)).consume(Atom.class);
        return stack.push(r);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// [p] -> r

public final class Cnullary implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List p = stack.consume(List.class);
        Atom r = context.interpreter().interpret(stack.copy(), context, p).consume(Atom.class);
        return stack.push(r);
    }
}

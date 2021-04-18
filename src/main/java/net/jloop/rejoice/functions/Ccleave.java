package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x [p] [q] -> ~rp ~rq

public final class Ccleave implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        Atom rp = p.invoke(stack.copy(), context).consume(Atom.class);
        Atom rq = q.invoke(stack.copy(), context).consume(Atom.class);
        return stack.pop().push(rp).push(rq);
    }
}

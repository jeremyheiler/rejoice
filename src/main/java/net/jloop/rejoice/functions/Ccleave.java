package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// x [p] [q] -> ~rp ~rq

public final class Ccleave implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        Atom rp = p.invoke(env, stack.copy()).consume(Atom.class);
        Atom rq = q.invoke(env, stack.copy()).consume(Atom.class);
        return stack.pop().push(rp).push(rq);
    }
}

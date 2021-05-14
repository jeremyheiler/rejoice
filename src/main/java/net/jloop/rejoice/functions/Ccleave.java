package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// x [p] [q] -> ~rp ~rq

public final class Ccleave implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote q = stack.consume(Quote.class);
        Quote p = stack.consume(Quote.class);
        Atom rp = p.call(env, stack.copy()).consume(Atom.class);
        Atom rq = q.call(env, stack.copy()).consume(Atom.class);
        return stack.pop().push(rp).push(rq);
    }
}

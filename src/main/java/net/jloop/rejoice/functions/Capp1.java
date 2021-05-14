package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// x [p] -> r
// Replace x with the result of evaluating the quote p with a stack containing only x.

public final class Capp1 implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote p = stack.consume(Quote.class);
        Atom x = stack.consume(Atom.class);
        Atom r = p.call(env, new Stack().push(x)).consume(Atom.class);
        return stack.push(r);
    }
}

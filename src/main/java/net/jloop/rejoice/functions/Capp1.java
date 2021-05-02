package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// x [p] -> r
// Replace x with the result of evaluating the quote p with a stack containing only x.

public final class Capp1 implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        Atom r = p.evaluate(env, new Stack().push(x)).consume(Atom.class);
        return stack.push(r);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// x y [p] -> rx ry
// Replace x and y with the results of evaluating the quote p with a stack containing only x or y, respectively.

public final class Capp2 implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = p.invoke(env, new Stack().push(y)).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = p.invoke(env, new Stack().push(x)).consume(Atom.class);
        return stack.push(rx).push(ry);
    }
}

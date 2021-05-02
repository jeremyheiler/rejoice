package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// x y z [p] -> rx ry rz
// Replace x, y, and z with the results of evaluating the quote p with a stack containing only x, y, or z, respectively.

public final class Capp3 implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom rz = p.evaluate(env, new Stack().push(z)).consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = p.evaluate(env, new Stack().push(y)).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = p.evaluate(env, new Stack().push(x)).consume(Atom.class);
        return stack.push(rx).push(ry).push(rz);
    }
}

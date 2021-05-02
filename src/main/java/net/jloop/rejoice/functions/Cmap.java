package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// a [p] -> b

public final class Cmap implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        List p = stack.consume(List.class);
        List a = stack.consume(List.class);
        List b = new List();
        for (Value value : a) {
            b.append(p.evaluate(env, new Stack().push(value)).consume(Atom.class));
        }
        return stack.push(b);
    }
}

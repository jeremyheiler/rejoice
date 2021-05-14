package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// a [p] -> b

public final class Cmap implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote p = stack.consume(Quote.class);
        Quote a = stack.consume(Quote.class);
        Quote b = new Quote();
        for (Value value : a) {
            b.append(p.call(env, new Stack().push(value)).consume(Atom.class));
        }
        return stack.push(b);
    }
}

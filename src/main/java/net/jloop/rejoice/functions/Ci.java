package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// [p] -> ...

public final class Ci implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote p = stack.consume(Quote.class);
        return p.call(env, stack);
    }
}

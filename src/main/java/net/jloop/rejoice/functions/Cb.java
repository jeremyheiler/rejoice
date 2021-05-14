package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// [p] [q] -> ~rp ~rq

public final class Cb implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote q = stack.consume(Quote.class);
        Quote p = stack.consume(Quote.class);
        stack = p.call(env, stack);
        stack = q.call(env, stack);
        return stack;
    }
}

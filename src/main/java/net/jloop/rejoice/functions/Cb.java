package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// [p] [q] -> ~rp ~rq

public final class Cb implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        stack = p.invoke(env, stack);
        stack = q.invoke(env, stack);
        return stack;
    }
}

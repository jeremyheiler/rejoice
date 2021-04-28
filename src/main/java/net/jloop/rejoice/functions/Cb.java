package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.List;

// [p] [q] -> ~rp ~rq

public final class Cb implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        stack = p.interpret(context, stack);
        stack = q.interpret(context, stack);
        return stack;
    }
}

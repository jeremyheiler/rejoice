package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// [p] [q] -> ~rp ~rq

public final class Cb implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        stack = context.interpreter().interpret(stack, context, p);
        stack = context.interpreter().interpret(stack, context, q);
        return stack;
    }
}

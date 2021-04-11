package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// [p] -> [p] ...

public final class Cx implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List p = stack.peek(List.class);
        return context.interpreter().interpret(stack, context, p);
    }
}

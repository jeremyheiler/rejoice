package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;

// [b] [d] -> ...

public final class Cwhile implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List d = stack.consume(List.class);
        List b = stack.consume(List.class);
        while (context.interpreter().interpret(stack, context, b).consume(Bool.class) == Bool.True) {
            stack = context.interpreter().interpret(stack, context, d);
        }
        return stack;
    }
}

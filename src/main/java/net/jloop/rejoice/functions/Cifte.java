package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;

// [b] [t] [f] -> ...

public final class Cifte implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List f = stack.consume(List.class);
        List t = stack.consume(List.class);
        List b = stack.consume(List.class);
        Stack copy = stack.copy();
        if (context.interpreter().interpret(copy, context, b).consume(Bool.class) == Bool.True) {
            return context.interpreter().interpret(stack, context, t);
        } else {
            return context.interpreter().interpret(stack, context, f);
        }
    }
}

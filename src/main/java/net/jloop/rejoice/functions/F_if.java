package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;

public final class F_if implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List f = stack.consume(List.class);
        List t = stack.consume(List.class);
        List b = stack.consume(List.class);
        Stack copy = stack.copy();
        if (b.invoke(context, copy).consume(Bool.class) == Bool.True) {
            return t.interpret(stack, context);
        } else {
            return f.interpret(stack, context);
        }
    }
}

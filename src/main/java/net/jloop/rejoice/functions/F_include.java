package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Str;

public final class F_include implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Str name = stack.consume(Str.class);
        context.current().include(context.get(name.get()));
        return stack;
    }
}

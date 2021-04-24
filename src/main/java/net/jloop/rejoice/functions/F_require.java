package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Str;

public final class F_require implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Str name = stack.consume(Str.class);
        context.active().require(context.modules().resolve(name.get()));
        return stack;
    }
}

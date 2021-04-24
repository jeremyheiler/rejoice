package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;

// [b] [d] -> ...

public final class Cwhile implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List d = stack.consume(List.class);
        List b = stack.consume(List.class);
        while (b.invoke(context, stack.copy()).consume(Bool.class) == Bool.True) {
            stack = d.invoke(context, stack);
        }
        return stack;
    }
}

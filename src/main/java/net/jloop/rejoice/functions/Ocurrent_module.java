package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Str;

public final class Ocurrent_module implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        return stack.push(new Str(context.current().name()));
    }
}

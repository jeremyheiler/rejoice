package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.List;

public final class F_cons implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        return stack.push(stack.consume(List.class).cons(stack.consume()));
    }
}

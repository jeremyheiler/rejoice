package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.List;

// [p] -> [p] ...

public final class Cx implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        return stack.peek(List.class).interpret(context, stack);
    }
}

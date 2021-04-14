package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

public final class F_cons implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        return stack.push(stack.consume(List.class).prepend(stack.consume()));
    }
}

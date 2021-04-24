package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;

public final class F_stack_promote implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        return stack.consume(Stack.class);
    }
}

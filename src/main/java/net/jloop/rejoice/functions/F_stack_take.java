package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Int64;

public final class F_stack_take implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Stack s = stack.peek(Stack.class);
        return stack.push(s.take((int) n.get()));
    }
}

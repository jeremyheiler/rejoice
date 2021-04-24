package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Int64;

public final class F_stack_get implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Stack s = stack.peek(Stack.class);
        return stack.push(s.get((int) n.get()));
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;

public final class F_stack_peek implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Stack s = stack.peek(Stack.class);
        Value x = s.peek();
        return stack.push(x);
    }
}

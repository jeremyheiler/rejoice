package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;

public final class F_stack_push implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Value x = stack.consume();
        Stack s = stack.consume(Stack.class);
        return stack.push(s.push(x));
    }
}

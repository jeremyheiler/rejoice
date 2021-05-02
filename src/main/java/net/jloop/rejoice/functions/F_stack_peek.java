package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Stack;

public final class F_stack_peek implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Stack s = stack.peek(Stack.class);
        Value x = s.peek();
        return stack.push(x);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Stack;

public final class F_stack_push implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Stack s = stack.consume(Stack.class);
        Value x = stack.consume();
        return stack.push(s.push(x));
    }
}

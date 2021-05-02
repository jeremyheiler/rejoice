package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Stack;

public final class F_stack_take implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Stack s = stack.peek(Stack.class);
        return stack.push(s.consume((int) n.get()));
    }
}

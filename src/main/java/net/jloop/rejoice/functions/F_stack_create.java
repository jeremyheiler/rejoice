package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Stack;

public class F_stack_create implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        long n = stack.consume(Int64.class).get();
        Stack s = new Stack();
        for (long i = 0; i < n; ++i) {
            s.push(stack.consume(Value.class).unquote());
        }
        return stack.push(s);
    }
}

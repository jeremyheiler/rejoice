package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Stack;

public class F_stack implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        long n = stack.consume(Int64.class).get();
        Stack target = new Stack();
        for (long i = 0; i < n; ++i) {
            Value value = stack.consume();
            target.push(value);
        }
        return stack.push(target);
    }
}

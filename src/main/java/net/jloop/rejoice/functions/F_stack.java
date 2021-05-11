package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

public class F_stack implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        long n = stack.consume(Int64.class).get();
        Stack s = new Stack();
        for (long i = 0; i < n; ++i) {
            Value value = stack.consume();
            if (value instanceof Quote) {
                value = ((Quote) value).unquote();
            }
            s.push(value);
        }
        return stack.push(s);
    }
}

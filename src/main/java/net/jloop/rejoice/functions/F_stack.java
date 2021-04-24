package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;

public class F_stack implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        long n = stack.consume(Int64.class).get();
        Stack s = new Stack();
        for (long i = 0; i < n; ++i) {
            s.push(stack.consume(Value.class).unquote(context.active()));
        }
        return stack.push(s);
    }
}

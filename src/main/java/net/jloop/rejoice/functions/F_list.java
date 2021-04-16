package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;

public class F_list implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        long n = stack.consume(Int64.class).get();
        List list = new List();
        for (long i = 0; i < n; ++i) {
            list.cons(stack.consume(Value.class).unquote(context.active()));
        }
        return stack.push(list);
    }
}

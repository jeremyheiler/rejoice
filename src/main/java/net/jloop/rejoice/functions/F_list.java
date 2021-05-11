package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

public class F_list implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        long n = stack.consume(Int64.class).get();
        List list = new List();
        for (long i = 0; i < n; ++i) {
            list.cons(stack.consume(Value.class).unquote());
        }
        return stack.push(list);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

public class F_length implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List list = stack.peek(List.class);
        return stack.push(new Int64(list.length()));
    }
}

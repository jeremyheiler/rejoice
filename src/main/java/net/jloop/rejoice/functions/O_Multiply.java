package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Stack;

// i j -> k

public final class O_Multiply implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.multiply(j);
        return stack.push(k);
    }
}

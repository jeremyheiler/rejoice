package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Int64;

// i j -> k

public final class Omin implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.min(j);
        return stack.push(k);
    }
}

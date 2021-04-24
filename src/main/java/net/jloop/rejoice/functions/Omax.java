package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Int64;

// i j -> k

public final class Omax implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.max(j);
        return stack.push(k);
    }
}

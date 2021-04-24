package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Int64;

// i j -> k

public final class O_Divide implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.divide(j);
        return stack.push(k);
    }
}

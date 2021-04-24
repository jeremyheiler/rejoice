package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Int64;

// n -> m

public final class Oabs implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.abs();
        return stack.push(m);
    }
}

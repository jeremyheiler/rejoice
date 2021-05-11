package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Stack;

// n -> m

public final class Osign implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.signum();
        return stack.push(m);
    }
}

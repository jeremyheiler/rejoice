package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// [p] -> ...

public final class Ci implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List p = stack.consume(List.class);
        return p.invoke(env, stack);
    }
}

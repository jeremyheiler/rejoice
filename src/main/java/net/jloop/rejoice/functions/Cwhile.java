package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// [b] [d] -> ...

public final class Cwhile implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        List d = stack.consume(List.class);
        List b = stack.consume(List.class);
        while (b.evaluate(env, stack.copy()).consume(Bool.class) == Bool.True) {
            stack = d.evaluate(env, stack);
        }
        return stack;
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// [b] [d] -> ...

public final class Cwhile implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote d = stack.consume(Quote.class);
        Quote b = stack.consume(Quote.class);
        while (b.call(env, stack.copy()).consume(Bool.class) == Bool.True) {
            stack = d.call(env, stack);
        }
        return stack;
    }
}

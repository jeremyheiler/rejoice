package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

public final class F_if implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote f = stack.consume(Quote.class);
        Quote t = stack.consume(Quote.class);
        Quote b = stack.consume(Quote.class);
        Stack copy = stack.copy();
        if (b.call(env, copy).consume(Bool.class) == Bool.True) {
            return t.call(env, stack);
        } else {
            return f.call(env, stack);
        }
    }
}

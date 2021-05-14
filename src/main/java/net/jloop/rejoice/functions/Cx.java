package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

// [p] -> [p] ...

public final class Cx implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        return stack.peek(Quote.class).call(env, stack);
    }
}

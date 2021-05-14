package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

public final class F_cons implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        return stack.push(stack.consume(Quote.class).cons(stack.consume()));
    }
}

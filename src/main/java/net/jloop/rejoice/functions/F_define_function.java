package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

public final class F_define_function implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Symbol name = stack.consume(Symbol.class);
        Quote body = stack.consume(Quote.class);
        env.define(name.name(), body);
        return stack;
    }
}

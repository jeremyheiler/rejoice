package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

public final class F_define_function implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        Symbol name = stack.consume(Quote.class).symbol();
        List body = stack.consume(List.class);
        env.define(name.name(), body);
        return stack;
    }
}

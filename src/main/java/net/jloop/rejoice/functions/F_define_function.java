package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.util.Deque;

public final class F_define_function implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Symbol name = data.consume(Symbol.class);
        Quote body = data.consume(Quote.class);
        env.define(name.name(), body);
        return data;
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

public final class F_apply implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        call.push(data.consume(Quote.class));
        return data;
    }
}

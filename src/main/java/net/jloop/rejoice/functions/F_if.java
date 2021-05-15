package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.util.Deque;

public final class F_if implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Quote f = data.consume(Quote.class);
        Quote t = data.consume(Quote.class);
        Quote pred = data.consume(Quote.class);
        call.push(t);
        call.push(f);
        call.push(pred.append(Symbol.of("prepare-if")));
        return data;
    }
}

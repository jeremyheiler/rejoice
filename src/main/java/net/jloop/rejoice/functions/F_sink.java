package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

public final class F_sink implements Function {
    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Int64 n = data.consume(Int64.class);
        Value value = data.consume((int) n.get());
        return data.push(value);
    }
}

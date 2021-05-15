package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

// i j -> k

public final class O_Plus implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Int64 j = data.consume(Int64.class);
        Int64 i = data.consume(Int64.class);
        Int64 k = i.plus(j);
        return data.push(k);
    }
}

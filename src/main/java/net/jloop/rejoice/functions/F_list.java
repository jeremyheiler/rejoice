package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

public class F_list implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        long n = data.consume(Int64.class).get();
        Quote target = new Quote();
        for (long i = 0; i < n; ++i) {
            Value value = data.consume();
            target.cons(value);
        }
        return data.push(target);
    }
}

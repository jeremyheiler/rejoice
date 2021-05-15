package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

// This function assumes there is a quote on the call stack
// that should only be evaluated if the boolean 'true' is on
// the data stack.

public final class F_prepare_when implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Bool bool = data.consume(Bool.class);
        if (bool == Bool.False) call.pop();
        return data;
    }
}

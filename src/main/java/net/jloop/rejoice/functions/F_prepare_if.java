package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

// This function assumes there are "then" and "else" quotes on
// the call stack. Which one is kept to be evaluated depends on
// the boolean value that is found on the top of the data stack.

public final class F_prepare_if implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Bool bool = data.consume(Bool.class);
        if (bool == Bool.True) {
            // Remove the "else" quote from the call stack
            Quote top = call.pop();
            call.pop();
            call.push(top);
        } else {
            // Remove the "then" quote from the call stack
            call.pop();
        }
        return data;
    }
}

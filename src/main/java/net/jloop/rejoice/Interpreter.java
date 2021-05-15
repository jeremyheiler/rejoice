package net.jloop.rejoice;

import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.util.Deque;

public final class Interpreter {

    public Stack interpret(Env env, Stack data, Deque<Quote> call) {
        while (!call.isEmpty()) {
            Quote quote = call.pop();
            if (quote != null) {
                Value next = quote.first();
                call.push(quote.rest());
                if (next instanceof Symbol) {
                    Symbol symbol = (Symbol) next;
                    env.trace().push(symbol);
                    Function function = env.lookup(symbol);
                    data = function.call(env, data, call);
                    env.trace().pop();
                } else {
                    data.push(next);
                }
            }
        }
        return data;
    }
}

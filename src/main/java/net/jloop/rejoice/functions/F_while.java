package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.util.Deque;

// Example: 3 [ pos? ] [ dec ] while

public final class F_while implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Quote body = data.consume(Quote.class);
        Quote pred = data.consume(Quote.class);
        Quote recur = new Quote()
                .append(body).append(Symbol.of("apply"))
                .append(pred).append(body).append(Symbol.of("while"));
        call.push(new Quote().append(pred).append(recur).append(Symbol.of("when")));
        return data;
    }
}

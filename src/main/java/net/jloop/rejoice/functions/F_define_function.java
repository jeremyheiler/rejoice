package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

public final class F_define_function implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Quote quote = stack.consume(Quote.class);
        List body = stack.consume(List.class);
        env.add(quote.symbol(), body);
        return stack;
    }
}

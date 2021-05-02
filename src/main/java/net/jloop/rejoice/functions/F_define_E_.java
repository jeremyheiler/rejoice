package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

public final class F_define_E_ implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Quote quote = stack.consume(Quote.class);
        List body = stack.consume(List.class);
        env.active().define(quote.name(), body);
        return stack;
    }
}

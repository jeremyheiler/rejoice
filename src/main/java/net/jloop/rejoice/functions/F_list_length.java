package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

public class F_list_length implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Quote quote = stack.peek(Quote.class);
        return stack.push(new Int64(quote.length()));
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;

public final class F_define_E_ implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Quote quote = stack.consume(Quote.class);
        List body = stack.consume(List.class);
        context.active().define(quote.name(), body);
        return stack;
    }
}

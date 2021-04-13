package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;

public final class Odefine_E_ implements Function {

    private final boolean pub;

    public Odefine_E_(boolean pub) {
        this.pub = pub;
    }

    @Override
    public Stack invoke(Stack stack, Context context) {
        Quote name = stack.consume(Quote.class);
        List body = stack.consume(List.class);
        Function function = new Function.Interpreted(body);
        context.current().define(name.get().name(), function, pub);
        return stack;
    }
}

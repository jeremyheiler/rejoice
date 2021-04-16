package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

public final class F_define_E_ implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Symbol name = stack.consume(Symbol.class);
        List body = stack.consume(List.class);
        Function function = new Function.Interpreted(body);
        context.active().define(name.name(), function);
        return stack;
    }
}

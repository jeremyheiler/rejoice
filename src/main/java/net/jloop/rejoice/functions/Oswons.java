package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.protocols.Cons;

// Cons x -> Cons

public final class Oswons implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Value x = stack.consume();
        Cons a = stack.consume(Cons.class);
        a.cons(x);
        return stack.push(a);
    }
}

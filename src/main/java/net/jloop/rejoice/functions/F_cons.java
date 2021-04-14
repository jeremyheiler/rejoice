package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.protocols.Cons;

public final class F_cons implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Cons a = stack.consume(Cons.class);
        Value x = stack.consume();
        a.cons(x);
        return stack.push(a);
    }
}

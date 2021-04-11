package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;

// Cons -> Boolean
// Int64 -> Boolean

public final class Onull implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        if (stack.check(Int64.class)) {
            Int64 n = stack.consume(Int64.class);
            return stack.push(Bool.of(n.get() == 0));
        } else {
            List list = stack.consume(List.class);
            return stack.push(Bool.of(list.isEmpty()));
        }
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.Value;

public final class F_write implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Value value = stack.peek();
        System.out.print(value.write());
        return stack;
    }
}

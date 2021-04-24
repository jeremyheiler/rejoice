package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

public final class F_pop implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        return stack.pop();
    }
}

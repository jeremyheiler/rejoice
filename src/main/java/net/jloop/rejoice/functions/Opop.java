package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

// x ->

public final class Opop implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        return stack.pop();
    }
}

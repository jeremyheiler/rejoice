package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

public final class O_PrintStack implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        stack.print();
        return stack;
    }
}

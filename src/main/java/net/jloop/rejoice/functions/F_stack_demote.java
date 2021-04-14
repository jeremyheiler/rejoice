package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

public final class F_stack_demote implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        return new Stack().push(stack);
    }
}

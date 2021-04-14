package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

public final class F_stack_apply implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Stack s = stack.consume(Stack.class);
        List p = stack.consume(List.class);
        return stack.push(context.interpreter().interpret(s, context, p));
    }
}

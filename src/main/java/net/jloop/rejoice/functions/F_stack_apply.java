package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

public final class F_stack_apply implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        Stack s = stack.consume(Stack.class);
        List p = stack.consume(List.class);
        return stack.push(p.invoke(env, s));
    }
}

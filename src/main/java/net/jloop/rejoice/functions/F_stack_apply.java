package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

public final class F_stack_apply implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Stack s = stack.consume(Stack.class);
        Quote p = stack.consume(Quote.class);
        return stack.push(p.call(env, s));
    }
}

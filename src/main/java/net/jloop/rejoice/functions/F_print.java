package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Stack;

public final class F_print implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Value value = stack.peek();
        System.out.print(value.print());
        return stack;
    }
}

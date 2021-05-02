package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;

public final class F_pop implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        return stack.pop();
    }
}

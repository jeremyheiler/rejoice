package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;

public final class F_demote implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        return new Stack().push(stack);
    }
}

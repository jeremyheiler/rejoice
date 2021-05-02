package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Str;

public final class F_include implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Str name = stack.consume(Str.class);
        env.active().include(env.module(name.get()));
        return stack;
    }
}

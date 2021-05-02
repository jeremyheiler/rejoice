package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Module;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Str;

public final class F_module implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Str name = stack.consume(Str.class);
        Module module = new Module(name.get());
        module.require(env.module("core"));
        env.install(module);
        env.activate(module);
        return stack;
    }
}

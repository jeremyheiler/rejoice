package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Protocol;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

public final class F_define_protocol implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Symbol name = stack.consume(Symbol.class);
        Protocol protocol = new Protocol(name);
        env.define(name, protocol);
        return stack;
    }
}

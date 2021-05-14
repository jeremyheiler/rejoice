package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Invocable;
import net.jloop.rejoice.Protocol;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

public final class F_extend_protocol implements Function {

    @Override
    public Stack call(Env env, Stack stack) {
        Type type = stack.consume(Type.class);
        Symbol name = stack.consume(Symbol.class);
        Quote body = stack.consume(Quote.class);
        Invocable invocable = env.lookup(name);
        if (!(invocable instanceof Protocol)) {
            throw new RuntimeError("INTERPRET", "No protocol with the name '" + name.name() + "'");
        }
        Protocol protocol = (Protocol) invocable;
        protocol.extend(type, body);
        return stack;
    }
}

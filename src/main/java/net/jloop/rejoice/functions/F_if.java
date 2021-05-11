package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

public final class F_if implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List f = stack.consume(List.class);
        List t = stack.consume(List.class);
        List b = stack.consume(List.class);
        Stack copy = stack.copy();
        if (b.invoke(env, copy).consume(Bool.class) == Bool.True) {
            return t.invoke(env, stack);
        } else {
            return f.invoke(env, stack);
        }
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// [p] -> [p] ...

public final class Cx implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        return stack.peek(List.class).invoke(env, stack);
    }
}

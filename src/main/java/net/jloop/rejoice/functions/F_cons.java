package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

public final class F_cons implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        return stack.push(stack.consume(List.class).cons(stack.consume()));
    }
}

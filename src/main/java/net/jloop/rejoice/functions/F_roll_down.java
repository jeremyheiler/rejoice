package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Stack;

// x y z -> y z x

public final class F_roll_down implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Value z = stack.consume();
        Value y = stack.consume();
        Value x = stack.consume();
        return stack.push(y).push(z).push(x);
    }
}

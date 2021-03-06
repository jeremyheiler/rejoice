package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.Value;

// x y z -> z x y

public final class F_roll_up implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Value z = stack.consume();
        Value y = stack.consume();
        Value x = stack.consume();
        return stack.push(z).push(x).push(y);
    }
}

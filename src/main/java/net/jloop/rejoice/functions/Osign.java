package net.jloop.rejoice.functions;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Int64;

// n -> m

public final class Osign implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.signum();
        return stack.push(m);
    }
}

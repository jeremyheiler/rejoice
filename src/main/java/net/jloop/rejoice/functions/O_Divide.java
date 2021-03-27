package net.jloop.rejoice.functions;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Int64;

// i j -> k

public final class O_Divide implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.divide(j);
        return stack.push(k);
    }
}

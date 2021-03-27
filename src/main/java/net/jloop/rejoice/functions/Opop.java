package net.jloop.rejoice.functions;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;

// x ->

public final class Opop implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        return stack.pop();
    }
}

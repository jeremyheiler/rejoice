package net.jloop.rejoice.functions;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;

public final class O_E_ implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        stack.print();
        return stack;
    }
}

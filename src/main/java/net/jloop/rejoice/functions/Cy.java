package net.jloop.rejoice.functions;

import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;

public final class Cy implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        throw new UnsupportedOperationException("y");
    }
}

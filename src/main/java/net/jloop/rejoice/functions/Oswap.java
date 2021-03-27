package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;

// x y -> y x

public final class Oswap implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(x);
    }
}

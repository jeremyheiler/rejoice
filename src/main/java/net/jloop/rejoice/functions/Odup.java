package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;

// x -> x x

public final class Odup implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Atom x = stack.peek(Atom.class);
        return stack.push(x);
    }
}

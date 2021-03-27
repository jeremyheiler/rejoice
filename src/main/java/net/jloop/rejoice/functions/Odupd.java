package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;

// x y -> x x y

public final class Odupd implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.peek(Atom.class);
        return stack.push(x).push(y);
    }
}

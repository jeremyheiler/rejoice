package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;

// x y -> y

public final class Opopd implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Atom x = stack.peek(Atom.class);
        return stack.pop().pop().push(x);
    }
}

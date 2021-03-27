package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;

// x y -> b

public final class Oequal_Q_ implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Bool b = new Bool(x.equals(y));
        return stack.push(b);
    }
}

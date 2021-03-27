package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;

// b t f -> r
// If b is true, leave t on the stack. If b is false, leave f on the stack.

public final class Ochoice implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Atom f = stack.consume(Atom.class);
        Atom t = stack.consume(Atom.class);
        Bool b = stack.consume(Bool.class);
        Atom r = b.isTrue() ? t : f;
        return stack.push(r);
    }
}

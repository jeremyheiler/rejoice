package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;

// b t f -> r
// If b is true, leave t on the stack. If b is false, leave f on the stack.

public final class Ochoice implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Atom f = stack.consume(Atom.class);
        Atom t = stack.consume(Atom.class);
        Bool b = stack.consume(Bool.class);
        Atom r = b == Bool.True ? t : f;
        return stack.push(r);
    }
}

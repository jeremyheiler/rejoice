package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;

// x y z -> z x y

public final class Orollup implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(z).push(x).push(y);
    }
}

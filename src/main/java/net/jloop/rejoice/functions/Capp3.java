package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.List;

// x y z [p] -> rx ry rz
// Replace x, y, and z with the results of evaluating the quote p with a stack containing only x, y, or z, respectively.

public final class Capp3 implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom rz = p.interpret(context, new Stack().push(z)).consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = p.interpret(context, new Stack().push(y)).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = p.interpret(context, new Stack().push(x)).consume(Atom.class);
        return stack.push(rx).push(ry).push(rz);
    }
}

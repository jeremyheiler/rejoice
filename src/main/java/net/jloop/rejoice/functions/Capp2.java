package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x y [p] -> rx ry
// Replace x and y with the results of evaluating the quote p with a stack containing only x or y, respectively.

public final class Capp2 implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = context.interpreter().interpret(new Stack().push(y), context, p).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = context.interpreter().interpret(new Stack().push(x), context, p).consume(Atom.class);
        return stack.push(rx).push(ry);
    }
}

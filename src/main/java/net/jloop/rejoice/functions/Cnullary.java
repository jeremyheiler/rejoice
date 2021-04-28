package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.List;

// [p] -> r

public final class Cnullary implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List p = stack.consume(List.class);
        Atom r = p.interpret(context, stack.copy()).consume(Atom.class);
        return stack.push(r);
    }
}

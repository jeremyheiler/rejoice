package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.List;

// a [p] -> b

public final class Cmap implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        List p = stack.consume(List.class);
        List a = stack.consume(List.class);
        List b = new List();
        for (Value value : a) {
            b.append(p.invoke(context, new Stack().push(value)).consume(Atom.class));
        }
        return stack.push(b);
    }
}

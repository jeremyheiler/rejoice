package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

// [p] -> r

public final class Cnullary implements Function {

    @Override
    public Stack invoke(Env env, Stack stack) {
        List p = stack.consume(List.class);
        Atom r = p.invoke(env, stack.copy()).consume(Atom.class);
        return stack.push(r);
    }
}

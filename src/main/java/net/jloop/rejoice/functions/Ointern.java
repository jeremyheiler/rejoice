package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;

// String -> Symbol

public final class Ointern implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Str s = stack.consume(Str.class);
        return stack.push(Symbol.of(s.get()));
    }
}

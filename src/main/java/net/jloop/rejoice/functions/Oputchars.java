package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Str;

public final class Oputchars implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Str string = stack.consume(Str.class);
        System.out.print(string.get());
        return stack;
    }
}
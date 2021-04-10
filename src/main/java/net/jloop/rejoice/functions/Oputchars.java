package net.jloop.rejoice.functions;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Str;

public final class Oputchars implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Str string = stack.consume(Str.class);
        System.out.print(string.get());
        return stack;
    }
}

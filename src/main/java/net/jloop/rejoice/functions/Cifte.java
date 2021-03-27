package net.jloop.rejoice.functions;

import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;

// [b] [t] [f] -> ...

public final class Cifte implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List f = stack.consume(List.class);
        List t = stack.consume(List.class);
        List b = stack.consume(List.class);
        Stack copy = stack.copy();
        if (interpreter.interpret(copy, b).consume(Bool.class).isTrue()) {
            return interpreter.interpret(stack, t);
        } else {
            return interpreter.interpret(stack, f);
        }
    }
}

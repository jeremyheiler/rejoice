package net.jloop.rejoice.functions;

import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;

// [b] [d] -> ...

public final class Cwhile implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List d = stack.consume(List.class);
        List b = stack.consume(List.class);
        while (interpreter.interpret(stack, b).consume(Bool.class).isTrue()) {
            stack = interpreter.interpret(stack, d);
        }
        return stack;
    }
}

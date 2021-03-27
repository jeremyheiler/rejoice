package net.jloop.rejoice.functions;

import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// [p] -> [p] ...

public final class Cx implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List p = stack.peek(List.class);
        return interpreter.interpret(stack, p);
    }
}

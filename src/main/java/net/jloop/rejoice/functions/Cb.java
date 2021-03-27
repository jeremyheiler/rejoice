package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// [p] [q] -> ~rp ~rq

public final class Cb implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        stack = interpreter.interpret(stack, p);
        stack = interpreter.interpret(stack, q);
        return stack;
    }
}

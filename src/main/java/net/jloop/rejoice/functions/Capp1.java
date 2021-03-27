package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x [p] -> r
// Replace x with the result of evaluating the quote p with a stack containing only x.

public final class Capp1 implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        Atom r = interpreter.interpret(new Stack().push(x), p).consume(Atom.class);
        return stack.push(r);
    }
}

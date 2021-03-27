package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x y [p] -> rx ry
// Replace x and y with the results of evaluating the quote p with a stack containing only x or y, respectively.

public final class Capp2 implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = interpreter.interpret(new Stack().push(y), p).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = interpreter.interpret(new Stack().push(x), p).consume(Atom.class);
        return stack.push(rx).push(ry);
    }
}

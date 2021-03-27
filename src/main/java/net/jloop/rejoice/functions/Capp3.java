package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x y z [p] -> rx ry rz
// Replace x, y, and z with the results of evaluating the quote p with a stack containing only x, y, or z, respectively.

public final class Capp3 implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom rz = interpreter.interpret(new Stack().push(z), p).consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = interpreter.interpret(new Stack().push(y), p).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = interpreter.interpret(new Stack().push(x), p).consume(Atom.class);
        return stack.push(rx).push(ry).push(rz);
    }
}

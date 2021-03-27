package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x [p] [q] -> ~rp ~rq

public final class Ccleave implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        Stack copyP = stack.copy();
        Stack copyQ = stack.copy();
        Atom rp = interpreter.interpret(copyP, p).consume(Atom.class);
        Atom rq = interpreter.interpret(copyQ, q).consume(Atom.class);
        return stack.pop().push(rp).push(rq);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Combinator;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.List;

// a [p] -> b

public final class Cmap implements Combinator {

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        List a = stack.consume(List.class);
        List b = new List();
        for (Value value : a) {
            b.append(interpreter.interpret(new Stack().push(value), p).consume(Atom.class));
        }
        return stack.push(b);
    }
}

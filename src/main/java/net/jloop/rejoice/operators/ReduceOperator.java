package net.jloop.rejoice.operators;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Library;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.literals.Quote;
import net.jloop.rejoice.literals.Symbol;

// [1 2 3] 0 + reduce --> 6

public class ReduceOperator implements Operator {

    @Override
    public Stack evaluate(Library library, Stack stack) {
        Quote name = stack.peek(Quote.class);
        stack = stack.drop();
        Operator operator = library.lookup(name.unquote(Symbol.class));
        Atom start = stack.peek(Atom.class);
        stack = stack.drop();
        Stack list = stack.peek(Stack.class);
        stack = stack.drop();
        Atom result = start;
        for (Atom atom : list) {
            Stack args = new Stack();
            args = args.push(result);
            args = args.push(atom);
            result = operator.evaluate(library, args).peek(Atom.class);
        }
        return stack.push(result);
    }
}

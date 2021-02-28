package net.jloop.rejoice.operators;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Library;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.literals.Quote;
import net.jloop.rejoice.literals.Symbol;

// [1 2] + apply --> 3

public class ApplyOperator implements Operator {

    @Override
    public Stack evaluate(Library library, Stack stack) {
        Quote name = stack.peek(Quote.class);
        stack = stack.drop();
        Operator operator = library.lookup(name.unquote(Symbol.class));
        Stack args = stack.peek(Stack.class);
        stack = stack.drop();
        Stack result = operator.evaluate(library, args);
        return stack.push(result.peek(Atom.class));
    }
}

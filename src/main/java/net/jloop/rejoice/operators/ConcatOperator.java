package net.jloop.rejoice.operators;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Library;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.literals.Str;

public class ConcatOperator implements Operator {

    @Override
    public Stack evaluate(Library library, Stack stack) {
        Str x = stack.peek(Str.class);
        stack = stack.drop();
        Str y = stack.peek(Str.class);
        stack = stack.drop();
        Str result = y.concat(x);
        return stack.push(result);
    }
}

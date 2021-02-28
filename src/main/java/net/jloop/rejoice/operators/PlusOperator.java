package net.jloop.rejoice.operators;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Library;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.literals.Int64;

public class PlusOperator implements Operator {

    @Override
    public Stack evaluate(Library library, Stack stack) {
        Int64 x = stack.peek(Int64.class);
        stack = stack.drop();
        Int64 y = stack.peek(Int64.class);
        stack = stack.drop();
        Int64 result = y.plus(x);
        return stack.push(result);
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;

public class Olist implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        long n = stack.consume(Int64.class).get();
        List list = new List();
        for (long i = 0; i < n; ++i) {
            Value value = stack.consume(Value.class);
            if (value instanceof Quote) {
                list.prepend(((Quote) value).get());
            } else {
                list.prepend(value);
            }
        }
        return stack.push(list);
    }
}

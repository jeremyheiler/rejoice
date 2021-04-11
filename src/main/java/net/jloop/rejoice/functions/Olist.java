package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;

public class Olist implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        long n = stack.consume(Int64.class).get();
        List list = new List();
        for (long i = 0; i < n; ++i) {
            Value value = stack.consume(Value.class);
            // TODO(jeremy) This is wrong. Not all quoted symbols should be unquoted.
            // Only the ones that were quoted in the result of splicing the list.
            if (value instanceof Quote) {
                value = ((Quote) value).get();
            }
            list.prepend(value);
        }
        return stack.push(list);
    }
}

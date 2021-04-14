package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Symbol;

public class F_stack implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        long n = stack.consume(Int64.class).get();
        Stack s = new Stack();
        for (long i = 0; i < n; ++i) {
            Value value = stack.consume(Value.class);
            if (value instanceof Symbol && ((Symbol) value).isQuoted()) {
                ((Symbol) value).unquote();
            }
            s.push(value);
        }
        return stack.push(s);
    }
}

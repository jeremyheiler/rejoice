package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

public final class Interpreter {

    public Stack interpret(Stack stack, Context context, Iterable<? extends Value> input) {
        for (Value value : input) {
            if (value instanceof Symbol) {
                stack = context.current().lookup(context, (Symbol) value).invoke(stack, context);
            } else {
                stack.push(value);
            }
        }
        return stack;
    }
}

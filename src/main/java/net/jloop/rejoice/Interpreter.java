package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

public final class Interpreter {

    public Stack interpret(Stack stack, Context context, Iterable<? extends Value> input) {
        for (Value value : input) {
            if (value instanceof Symbol && !((Symbol) value).isQuoted()) {
                context.trace().add(0, (Symbol) value); // TODO: Realize fully-qualified name
                stack = context.current().lookup(context, (Symbol) value).invoke(stack, context);
                context.trace().remove(0);
            } else {
                stack.push(value);
            }
        }
        return stack;
    }
}

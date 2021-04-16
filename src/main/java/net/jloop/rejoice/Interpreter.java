package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

public final class Interpreter {

    public Stack interpret(Stack stack, Context context, Iterable<? extends Value> stream) {
        for (Value value : stream) {
            if (value instanceof Symbol && !((Symbol) value).isQuoted()) {
                Module.Resolved resolved = context.resolve((Symbol) value);
                context.trace().add(resolved.toCall());
                stack = resolved.function().invoke(stack, context);
                context.trace().pop();
            } else {
                stack.push(value);
            }
        }
        return stack;
    }
}

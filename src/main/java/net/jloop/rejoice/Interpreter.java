package net.jloop.rejoice;

public final class Interpreter {

    public Stack interpret(Stack stack, Context context, Iterable<? extends Value> stream) {
        for (Value value : stream) {
            stack = value.interpret(stack, context);
        }
        return stack;
    }
}

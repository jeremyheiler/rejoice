package net.jloop.rejoice;

import java.util.Iterator;

public final class Interpreter {

    private final Context context;

    public Interpreter(Context context) {
        this.context = context;
    }

    public Stack reduce(Stack stack, Iterator<Atom> input) {
        while (input.hasNext()) {
            Atom next = input.next();
            stack = next.interpret(stack, context);
        }
        return stack;
    }
}

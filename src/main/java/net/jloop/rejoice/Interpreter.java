package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.ArrayList;
import java.util.Iterator;

public final class Interpreter {

    private final Context context;

    public Interpreter(Context context) {
        this.context = context;
    }

    public Stack reduce(Stack stack, Iterator<Atom> input) {
        ArrayList<Atom> rewritten = new ArrayList<>();
        while (true) {
            if (rewritten.isEmpty()) {
                if (input.hasNext()) {
                    input = input.next().rewrite(context, rewritten, input);
                } else {
                    break;
                }
            } else {
                stack = rewritten.remove(0).interpret(context, stack);
            }
        }
        return stack;
    }
}

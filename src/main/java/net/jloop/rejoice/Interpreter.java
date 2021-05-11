package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;

public final class Interpreter {

    public Stack interpret(Env env, Stack stack, Iterator<Value> input) {
        while (input.hasNext()) {
            Value next = input.next();
            if (next instanceof Symbol) {
                Symbol symbol = (Symbol) next;
                env.trace().push(symbol);
                Invocable invocable = env.lookup(symbol);
                stack = invocable.invoke(env, stack, input);
                env.trace().pop();
            } else {
                stack.push(next);
            }
        }
        return stack;
    }
}

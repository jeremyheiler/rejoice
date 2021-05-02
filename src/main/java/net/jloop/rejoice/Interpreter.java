package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.Iterator;

public final class Interpreter {

    public Stack interpret(Env env, Stack stack, Iterator<Value> input) {
        while (input.hasNext()) {
            Value value = input.next();
            stack = value.interpret(env, stack, input);
        }
        return stack;
    }
}

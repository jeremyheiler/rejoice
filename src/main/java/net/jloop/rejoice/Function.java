package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.Iterator;

@FunctionalInterface
public interface Function extends Invocable {

    Stack evaluate(Env env, Stack stack);

    @Override
    default Stack invoke(Env env, Stack stack, Iterator<Value> input) {
        return evaluate(env, stack);
    }
}

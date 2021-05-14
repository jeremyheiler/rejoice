package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.Iterator;

public interface Function extends Invocable {

    Stack call(Env env, Stack stack);

    @Override
    default Stack invoke(Env env, Stack stack, Iterator<Value> input) {
        return call(env, stack);
    }
}

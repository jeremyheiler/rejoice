package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.Iterator;

public interface Macro extends Invocable {

    Iterator<Value> call(Env env, Iterator<Value> input);

    @Override
    default Stack invoke(Env env, Stack stack, Iterator<Value> input) {
        return new Interpreter().interpret(env, stack, call(env, input));
    }
}

package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.Iterator;

public interface Invocable {

    Stack invoke(Env env, Stack stack, Iterator<Value> input);
}

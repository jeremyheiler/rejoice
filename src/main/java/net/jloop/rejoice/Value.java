package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.Iterator;
import java.util.List;

public interface Value {

    default void collect(Env env, Iterator<Value> input, List<Value> output) {
        output.add(this);
    }

    default Stack interpret(Env env, Stack stack, Iterator<Value> input) {
        return stack.push(this);
    }

    default Value quote() {
        return this;
    }

    default Value unquote(Env env) {
        return this;
    }

    // Prints the value such that it can be parsed again.
    String print();

    // Prints the value itself, i.e. '\x' prints 'x' and '"foo"' prints 'foo'.
    // Most values will print the same as print(), hence the default implementation.
    default String write() {
        return print();
    }
}

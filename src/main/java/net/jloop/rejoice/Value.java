package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

public interface Value {

    default Stack interpret(Stack stack, Context context) {
        return stack.push(this);
    }

    default Value quote() {
        return this;
    }

    default Value unquote(Module module) {
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

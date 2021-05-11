package net.jloop.rejoice;

import net.jloop.rejoice.types.Type;

public interface Value {

    Type type();

    // Prints the value such that it can be parsed again.
    String print();

    // Prints the value itself, i.e. '\x' prints 'x' and '"foo"' prints 'foo'.
    // Most values will print the same as print(), hence the default implementation.
    default String write() {
        return print();
    }
}

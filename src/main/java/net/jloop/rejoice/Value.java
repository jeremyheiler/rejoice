package net.jloop.rejoice;

public interface Value {

    default Value quote() {
        return this;
    }

    default Value unquote() {
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

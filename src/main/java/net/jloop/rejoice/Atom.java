package net.jloop.rejoice;

public interface Atom {

    Stack accept(Stack stack, Interpreter interpreter);
}

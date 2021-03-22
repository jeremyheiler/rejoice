package net.jloop.rejoice;

@FunctionalInterface
public interface Combinator {

    Stack evaluate(Stack stack, Interpreter interpreter);
}

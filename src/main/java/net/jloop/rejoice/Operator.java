package net.jloop.rejoice;

@FunctionalInterface
public interface Operator {

    Stack evaluate(Stack stack);
}

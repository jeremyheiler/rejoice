package net.jloop.rejoice;

@FunctionalInterface
public interface Atom {

    Stack evaluate(Library library, Stack stack);
}

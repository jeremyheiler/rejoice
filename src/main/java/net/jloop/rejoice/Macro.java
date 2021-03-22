package net.jloop.rejoice;

@FunctionalInterface
public interface Macro {

    Stack evaluate(Stack stack, Interpreter interpreter, Interpreter.Next next);
}

package net.jloop.rejoice;

@FunctionalInterface
public interface Macro extends Function {

    Stack evaluate(Stack stack, Interpreter interpreter, Interpreter.Next next);

    @Override
    default Stack invoke(Stack stack, Interpreter interpreter, Interpreter.Next next) {
        return evaluate(stack, interpreter, next);
    }
}

package net.jloop.rejoice;

@FunctionalInterface
public interface Combinator extends Function {

    Stack evaluate(Stack stack, Interpreter interpreter);

    @Override
    default Stack invoke(Stack stack, Interpreter interpreter, Interpreter.Next next) {
        return evaluate(stack, interpreter);
    }
}

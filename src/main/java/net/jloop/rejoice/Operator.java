package net.jloop.rejoice;

@FunctionalInterface
public interface Operator extends Function {

    Stack evaluate(Stack stack);

    @Override
    default Stack invoke(Stack stack, Interpreter interpreter, Interpreter.Next next) {
        return evaluate(stack);
    }
}

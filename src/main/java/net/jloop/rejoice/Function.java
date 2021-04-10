package net.jloop.rejoice;

import net.jloop.rejoice.types.List;

@FunctionalInterface
public interface Function {

    Stack invoke(Stack stack, Interpreter interpreter);

    static Function of(List body) {
        return (stack, interpreter) -> interpreter.interpret(stack, body);
    }
}

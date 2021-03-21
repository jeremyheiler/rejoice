package net.jloop.rejoice;

public interface Literal extends Atom {

    @Override
    default Stack accept(Stack stack, Interpreter interpreter) {
        return interpreter.evaluate(stack, this);
    }
}

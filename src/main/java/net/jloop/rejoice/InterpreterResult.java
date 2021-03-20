package net.jloop.rejoice;

public class InterpreterResult {

    private final Stack stack;
    private final RejoiceError error;

    public InterpreterResult(Stack stack, RejoiceError error) {
        this.stack = stack;
        this.error = error;
    }

    public Stack getStack() {
        return stack;
    }

    public RejoiceError getError() {
        return error;
    }
}

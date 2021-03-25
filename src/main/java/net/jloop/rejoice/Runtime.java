package net.jloop.rejoice;

import java.io.InputStreamReader;

public class Runtime {

    private final Interpreter interpreter;
    private Stack stack;

    public Runtime(Interpreter interpreter) {
        this(interpreter, new Stack());
    }

    public Runtime(Interpreter interpreter, Stack stack) {
        this.interpreter = interpreter;
        this.stack = stack;
    }

    public void init() {
        stack = interpreter.interpret(stack, new Input(new InputStreamReader(Runtime.class.getResourceAsStream("/core.rejoice"))));
    }

    public void eval(Input input) {
        stack = interpreter.interpret(stack, input);
    }

    public Stack stack() {
        return stack;
    }
}

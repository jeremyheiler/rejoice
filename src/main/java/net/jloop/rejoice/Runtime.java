package net.jloop.rejoice;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Runtime {

    private final String language;
    private final Interpreter interpreter;
    private Stack stack;

    public Runtime(String language, Interpreter interpreter) {
        this(language, interpreter, new Stack());
    }

    public Runtime(String language, Interpreter interpreter, Stack stack) {
        this.language = language;
        this.interpreter = interpreter;
        this.stack = stack;
    }

    public void load(InputStream stream) {
        stack = interpreter.interpret(stack, new Input(new InputStreamReader(stream)));
    }

    public void eval(Input input) {
        stack = interpreter.interpret(stack, input);
    }

    public String language() {
        return language;
    }

    public Stack stack() {
        return stack;
    }
}

package net.jloop.rejoice;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Runtime {

    private final String language;
    private final Interpreter interpreter;
    private Stack stack;

    public Runtime(String language, Library library) {
        this(language, library, new Stack());
    }

    public Runtime(String language, Library library, Stack stack) {
        this.language = language;
        this.interpreter = new Interpreter(library);
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

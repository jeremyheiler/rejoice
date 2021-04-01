package net.jloop.rejoice;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Runtime {

    private final String language;
    private final Interpreter interpreter;
    private final Compiler compiler;
    private final Parser parser;
    private final Lexer lexer;
    private Stack stack;

    public Runtime(String language, Interpreter interpreter, Compiler compiler, Parser parser, Lexer lexer) {
        this(language, interpreter, compiler, parser, lexer, new Stack());
    }

    public Runtime(String language, Interpreter interpreter, Compiler compiler, Parser parser, Lexer lexer, Stack stack) {
        this.language = language;
        this.interpreter = interpreter;
        this.compiler = compiler;
        this.parser = parser;
        this.lexer = lexer;
        this.stack = stack;
    }

    public void load(InputStream source) {
        eval(new Input(new InputStreamReader(source)));
    }

    public void eval(Input input) {
        Iterable<Lexer.Token> tokens = lexer.iterable(input);
        Iterable<Atom> atoms = parser.iterable(tokens.iterator());
        Iterable<Value> values = compiler.iterable(atoms.iterator());
        stack = interpreter.interpret(stack, values);
    }

    public String language() {
        return language;
    }

    public Stack stack() {
        return stack;
    }
}

package net.jloop.rejoice;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Runtime {

    private final String language;
    private final Interpreter interpreter;
    private final Rewriter rewriter;
    private final Parser parser;
    private final Lexer lexer;
    private Stack stack;

    public Runtime(String language, Interpreter interpreter, Rewriter rewriter, Parser parser, Lexer lexer) {
        this(language, interpreter, rewriter, parser, lexer, new Stack());
    }

    public Runtime(String language, Interpreter interpreter, Rewriter rewriter, Parser parser, Lexer lexer, Stack stack) {
        this.language = language;
        this.interpreter = interpreter;
        this.rewriter = rewriter;
        this.parser = parser;
        this.lexer = lexer;
        this.stack = stack;
    }

    public void load(InputStream source) {
        eval(new Input(new InputStreamReader(source)));
    }

    public void eval(Input input) {
        stack = interpreter.interpret(stack, rewriter.rewrite(parser.parse(lexer.lex(input))));
    }

    public String language() {
        return language;
    }

    public Stack stack() {
        return stack;
    }
}

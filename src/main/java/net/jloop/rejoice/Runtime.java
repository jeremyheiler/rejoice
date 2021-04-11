package net.jloop.rejoice;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Runtime {

    private final String language;
    private final Interpreter interpreter;
    private final Rewriter rewriter;
    private final Parser parser;
    private final Lexer lexer;
    private final Context context;
    private Stack stack;

    public Runtime(String language, Interpreter interpreter, Rewriter rewriter, Parser parser, Lexer lexer, Context context) {
        this(language, interpreter, rewriter, parser, lexer, context, new Stack());
    }

    public Runtime(String language, Interpreter interpreter, Rewriter rewriter, Parser parser, Lexer lexer, Context context, Stack stack) {
        this.language = language;
        this.interpreter = interpreter;
        this.rewriter = rewriter;
        this.parser = parser;
        this.lexer = lexer;
        this.context = context;
        this.stack = stack;
    }

    public void load(String moduleName, InputStream source) {
        load(new Module(moduleName), source);
    }

    public void load(Module module, InputStream source) {
        context.load(module);
        eval(new Input(new InputStreamReader(source)));
    }

    public void eval(Input input) {
        stack = interpreter.interpret(stack, context, rewriter.rewrite(parser.parse(lexer.lex(input)).iterator()));
    }

    public void eval(Function function) {
        stack = function.invoke(stack, context);
    }

    public String language() {
        return language;
    }

    public Context context() {
        return context;
    }

    public Stack stack() {
        return stack;
    }
}

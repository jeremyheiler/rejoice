package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.io.Reader;

public class Env {

    private final Lexer lexer = new Lexer();
    private final Parser parser = new Parser();
    private final Interpreter interpreter = new Interpreter();
    private final Trace trace = new Trace();
    private final Namespace proto;

    public Env(Namespace proto) {
        this.proto = proto;
    }

    public Stack eval(Stack stack, Reader input) {
        return interpreter.interpret(this, stack, parser.map(lexer.map(input)));
    }

    public Trace trace() {
        return trace;
    }

    public void add(Symbol symbol, Invocable invocable) {
        proto.add(symbol, invocable);
    }

    public Invocable resolve(Symbol symbol) {
        return proto.resolve(symbol);
    }
}

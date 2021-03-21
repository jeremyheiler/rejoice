package net.jloop.rejoice;

import java.io.PushbackReader;
import java.util.Optional;

public class Interpreter {

    private final Parser parser;
    private final Library library;

    public Interpreter(Parser parser, Library library) {
        this.parser = parser;
        this.library = library;
    }

    public InterpreterResult interpret(Stack stack, PushbackReader input) {
        ParserResult result;
        while ((result = parser.parse(input)).isOk()) {
            stack = result.getAtom().accept(stack, this);
        }
        if (result.isEof()) {
            return new InterpreterResult(stack, null);
        } else {
            return new InterpreterResult(stack, result.getError());
        }
    }

    public Stack evaluate(Stack stack, Literal literal) {
        return stack.push(literal);
    }

    public Stack evaluate(Stack stack, Symbol symbol) {
        Optional<Operator> operator = library.lookup(symbol);
        if (operator.isPresent()) {
            return operator.get().accept(stack, this);
        } else {
            throw new RuntimeException("Could not find operator for symbol " + symbol.getName());
        }
    }
}

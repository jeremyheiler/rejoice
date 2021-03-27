package net.jloop.rejoice;

import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

import java.util.Optional;

public final class Interpreter {

    private final Parser parser = new Parser();
    private final Library library;

    public Interpreter(Library library) {
        this.library = library;
    }

    public Library library() {
        return library;
    }

    public Stack interpret(Stack stack, Input input) {
        return interpret(stack, new NextFromInput(parser, input));
    }

    public Stack interpret(Stack stack, List list) {
        return interpret(stack, new NextFromList(list));
    }

    public Stack interpret(Stack stack, Next next) {
        Atom atom;
        while ((atom = next.next()) != null) {
            stack = interpret(stack, atom, next, Mode.Eval);
        }
        return stack;
    }

    public Stack interpret(Stack stack, Atom atom, Next next, Mode mode) {
        if (atom instanceof Symbol) {
            Symbol symbol = (Symbol) atom;
            Optional<Function> function = library.lookup(symbol);
            if (mode == Mode.Macro) {
                if (function.isPresent() && function.get() instanceof Macro) {
                    return function.get().invoke(stack, this, next);
                } else {
                    return stack.push(symbol);
                }
            } else {
                if (function.isPresent()) {
                    return function.get().invoke(stack, this, next);
                } else {
                    throw new RuntimeError("INTERPRET", "Could not find symbol '" + symbol.getName() + "' in the library");
                }
            }
        } else {
            return stack.push(atom);
        }
    }

    public enum Mode {
        Eval,
        Macro
    }

    public interface Next {

        Atom next();
    }

    private static final class NextFromList implements Next {

        private List list;

        public NextFromList(List list) {
            this.list = list;
        }

        @Override
        public Atom next() {
            Atom result = list.first();
            this.list = list.rest();
            return result;
        }
    }

    private static final class NextFromInput implements Next {

        private final Parser parser;
        private final Input input;

        public NextFromInput(Parser parser, Input input) {
            this.parser = parser;
            this.input = input;
        }

        @Override
        public Atom next() {
            return parser.parse(input);
        }
    }
}

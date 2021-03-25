package net.jloop.rejoice;

import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

public final class Macros {

    public static final class ListLiteral implements Macro {

        private final Symbol terminator;

        public ListLiteral(Symbol terminator) {
            this.terminator = terminator;
        }

        @Override
        public Stack evaluate(Stack stack, Interpreter interpreter, Interpreter.Next next) {
            Stack syntax = new Stack();

            Atom atom;
            while ((atom = next.next()) != null) {
                if (atom.equals(terminator)) {
                    return stack.push(List.from(syntax));
                }
                syntax = interpreter.interpret(syntax, atom, next, Interpreter.Mode.Macro);
            }
            throw new RuntimeError("MACRO", "Unexpected EOF; Run-on list");
        }
    }

    public static final class Define implements Macro {

        private final Symbol terminator;

        public Define(Symbol terminator) {
            this.terminator = terminator;
        }

        @Override
        public Stack evaluate(Stack stack, Interpreter interpreter, Interpreter.Next next) {
            Atom atom = next.next();
            if (atom == null) {
                throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete definition");
            }
            if (!(atom instanceof Symbol)) {
                throw new RuntimeError("MACRO", "Expecting a Symbol for the definition name, but found " + atom.getClass().getSimpleName());
            }
            Symbol name = (Symbol) atom;

            atom = next.next();
            if (atom == null) {
                throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete definition");
            }
            if (!atom.equals(Symbol.of(":"))) {
                throw new RuntimeError("MACRO", "Expecting a ':' after the definition name, but found " + atom.getClass().getSimpleName());
            }

            List body = new List();
            while ((atom = next.next()) != null) {
                if (atom.equals(terminator)) {
                    interpreter.library().operators().define(name, s -> interpreter.interpret(s, body));
                    return stack;
                }
                body.append(atom);
            }
            throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete definition");
        }
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

public final class Mdefine implements Macro {

    private final Symbol separator;
    private final Symbol terminator;

    public Mdefine(Symbol separator, Symbol terminator) {
        this.separator = separator;
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
        if (!atom.equals(separator)) {
            throw new RuntimeError("MACRO", "Expecting a ':' after the definition name, but found " + atom.getClass().getSimpleName());
        }

        List body = new List();
        while ((atom = next.next()) != null) {
            if (atom.equals(terminator)) {
                interpreter.library().define(name, (Operator) s -> interpreter.interpret(s, body));
                return stack;
            }
            body.append(atom);
        }
        throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete definition");
    }
}

package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

public final class Mlist implements Macro {

    private final Symbol terminator;

    public Mlist(Symbol terminator) {
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

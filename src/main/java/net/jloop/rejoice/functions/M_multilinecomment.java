package net.jloop.rejoice.functions;

import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Symbol;

public class M_multilinecomment implements Macro {

    private final Symbol terminator;

    public M_multilinecomment(Symbol terminator) {
        this.terminator = terminator;
    }

    @Override
    public Stack evaluate(Stack stack, Interpreter interpreter, Interpreter.Next next) {
        while (next.next() != terminator);
        return stack;
    }
}

package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.Map;

public final class Interpreter {

    private final Map<Symbol, Function> functions;

    public Interpreter(Map<Symbol, Function> functions) {
        this.functions = functions;
    }

    public Stack interpret(Stack stack, Iterable<? extends Value> input) {
        for (Value value : input) {
            if (value instanceof Symbol) {
                if (functions.containsKey(value)) {
                    stack = functions.get((Symbol) value).invoke(stack, this);
                } else {
                    throw new RuntimeError("INTERPRET", "Unable to resolve symbol '" + value.print() + "'");
                }
            } else {
                stack.push(value);
            }
        }
        return stack;
    }
}

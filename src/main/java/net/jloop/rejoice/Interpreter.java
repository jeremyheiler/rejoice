package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.Map;

public final class Interpreter {

    private final Map<Symbol, Function> functions;

    public Interpreter(Map<Symbol, Function> functions) {
        this.functions = functions;
    }

    public Stack interpret(Stack stack, Iterable<? extends Value> values) {
        for (Value value : values) {
            if (value instanceof Symbol) {
                Symbol symbol = (Symbol) value;
                if (functions.containsKey(value)) {
                    Function function = functions.get(value);
                    stack = function.invoke(stack, this);
                } else {
                    throw new RuntimeError("INTERPRET", "Unable to resolve symbol '" + symbol.getName() + "'");
                }
            } else {
                stack.push(value);
            }
        }
        return stack;
    }
}

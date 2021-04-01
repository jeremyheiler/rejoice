package net.jloop.rejoice.functions;

import net.jloop.rejoice.Function;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.Map;

public final class Odefine_E_ implements Operator {

    private final Map<Symbol, Function> functions;

    public Odefine_E_(Map<Symbol, Function> functions) {
        this.functions = functions;
    }

    @Override
    public Stack evaluate(Stack stack) {
        Quote quote = stack.consume(Quote.class);
        Value name = quote.get();
        if (!(name instanceof Symbol)) {
            throw new RuntimeError("INTERPRET", "Expecting a Symbol, but found " + name.getClass().getSimpleName());
        }
        List body = stack.consume(List.class);
        functions.put((Symbol) name, Function.of(body));
        return stack;
    }
}

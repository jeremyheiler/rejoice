package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Optional;

public class Library {

    private final HashMap<Symbol, Function> functions = new HashMap<>();

    public void define(Symbol symbol, Function function) {
        functions.put(symbol, function);
    }

    public void define(Symbol symbol, Operator operator) {
        functions.put(symbol, operator);
    }

    public void define(Symbol symbol, Combinator combinator) {
        functions.put(symbol, combinator);
    }

    public void define(Symbol symbol, Macro macro) {
        functions.put(symbol, macro);
    }

    public Optional<Function> lookup(Symbol symbol) {
        return Optional.ofNullable(functions.get(symbol));
    }
}

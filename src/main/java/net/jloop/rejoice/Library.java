package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Optional;

public class Library {

    private final HashMap<Symbol, Function> functions = new HashMap<>();

    public void define(Symbol symbol, Function function) {
        functions.put(symbol, function);
    }

    public Optional<Function> lookup(Symbol symbol) {
        return Optional.ofNullable(functions.get(symbol));
    }
}

package net.jloop.rejoice;

import java.util.HashMap;
import java.util.Optional;

public class Library {

    private final HashMap<Symbol, Operator> library = new HashMap<>();

    public void define(String name, Operator operator) {
        define(new Symbol(name), operator);
    }

    public void define(Symbol symbol, Operator operator) {
        library.put(symbol, operator);
    }

    public Optional<Operator> lookup(Symbol symbol) {
        return Optional.ofNullable(library.get(symbol));
    }
}

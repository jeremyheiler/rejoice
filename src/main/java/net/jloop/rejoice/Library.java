package net.jloop.rejoice;

import net.jloop.rejoice.literals.Symbol;

import java.util.HashMap;

public class Library {

    private final HashMap<Symbol, Operator> library = new HashMap<>();

    public void define(String name, Operator operator) {
        define(new Symbol(name), operator);
    }

    public void define(Symbol symbol, Operator operator) {
        library.put(symbol, operator);
    }

    public Operator lookup(Symbol symbol) {
        Operator operator =  library.get(symbol);
        if (operator == null) {
            throw new RuntimeException("Could not find operator for symbol " + symbol);
        }
        return operator;
    }
}

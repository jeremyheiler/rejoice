package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Optional;

public class Library {

    private final Definitions<Operator> operators = new Definitions<>();
    private final Definitions<Combinator> combinators = new Definitions<>();
    private final Definitions<Macro> macros = new Definitions<>();

    public Definitions<Operator> operators() {
        return operators;
    }

    public Definitions<Combinator> combinators() {
        return combinators;
    }

    public Definitions<Macro> macros() {
        return macros;
    }

    public static class Definitions<T> {

        private final HashMap<Symbol, T> defs = new HashMap<>();

        public void define(String symbol, T def) {
            define(Symbol.of(symbol), def);
        }

        public void define(Symbol symbol, T def) {
            defs.put(symbol, def);
        }

        public Optional<T> lookup(String symbol) {
            return lookup(Symbol.of(symbol));
        }

        public Optional<T> lookup(Symbol symbol) {
            return Optional.ofNullable(defs.get(symbol));
        }
    }
}

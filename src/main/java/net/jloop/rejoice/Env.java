package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Env {

    private final Map<Symbol, Function> functions = new HashMap<>();
    private final Trace trace = new Trace();

    public Trace trace() {
        return trace;
    }

    public void define(String name, Function function) {
        define(Symbol.of(Objects.requireNonNull(name)), Objects.requireNonNull(function));
    }

    public void define(Symbol name, Function function) {
        functions.put(Objects.requireNonNull(name), Objects.requireNonNull(function));
    }

    public Function lookup(Symbol symbol) {
        Function function = functions.get(Objects.requireNonNull(symbol));
        if (function == null) {
            throw new RuntimeError("INTERPRET", "Symbol '" + symbol.name() + "' is not defined");
        }
        return function;
    }
}

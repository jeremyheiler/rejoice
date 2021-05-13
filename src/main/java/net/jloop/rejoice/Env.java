package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Env {

    private final Map<Symbol, Invocable> invocables = new HashMap<>();
    private final Trace trace = new Trace();

    public Trace trace() {
        return trace;
    }

    public void define(String name, Invocable invocable) {
        define(Symbol.of(Objects.requireNonNull(name)), Objects.requireNonNull(invocable));
    }

    public void define(Symbol name, Invocable invocable) {
        invocables.put(Objects.requireNonNull(name), Objects.requireNonNull(invocable));
    }

    public Invocable lookup(Symbol symbol) {
        Invocable invocable = invocables.get(Objects.requireNonNull(symbol));
        if (invocable == null) {
            throw new RuntimeError("INTERPRET", "Symbol '" + symbol.name() + "' is not defined");
        }
        return invocable;
    }
}

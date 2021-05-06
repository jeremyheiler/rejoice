package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Collections;

public final class Trace {

    private final ArrayList<Symbol> calls = new ArrayList<>();

    public void push(Symbol symbol) {
        calls.add(symbol);
    }

    public void pop() {
        if (calls.isEmpty()) {
            throw new RuntimeError("INTERPRET", "Trace underflow");
        }
        calls.remove(calls.size() - 1);
    }

    public Symbol peek() {
        if (calls.isEmpty()) {
            throw new RuntimeError("INTERPRET", "Trace underflow");
        }
        return calls.get(calls.size() - 1);
    }

    public void clear() {
        calls.clear();
    }

    public Iterable<Symbol> calls() {
        ArrayList<Symbol> calls = new ArrayList<>(this.calls);
        Collections.reverse(calls);
        return calls;
    }
}

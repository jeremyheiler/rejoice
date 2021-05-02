package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Collections;

public final class Trace {

    private final ArrayList<Invocation> invocations = new ArrayList<>();

    public void push(Invocation invocation) {
        invocations.add(invocation);
    }

    public void pop() {
        if (invocations.isEmpty()) {
            throw new RuntimeError("INTERPRET", "Trace underflow");
        }
        invocations.remove(invocations.size() - 1);
    }

    public Invocation peek() {
        if (invocations.isEmpty()) {
            throw new RuntimeError("INTERPRET", "Trace underflow");
        }
        return invocations.get(invocations.size() - 1);
    }

    public void clear() {
        invocations.clear();
    }

    public Iterable<Invocation> invocations() {
        ArrayList<Invocation> invocations = new ArrayList<>(this.invocations);
        Collections.reverse(invocations);
        return invocations;
    }
}

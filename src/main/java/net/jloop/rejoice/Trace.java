package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Trace {

    private final ArrayList<Call> calls = new ArrayList<>();

    public void add(Call call) {
        calls.add(call);
    }

    public void pop() {
        if (calls.isEmpty()) {
            throw new RuntimeError("INTERPRET", "Trace underflow");
        }
        calls.remove(calls.size() - 1);
    }

    public Call peek() {
        if (calls.isEmpty()) {
            throw new RuntimeError("INTERPRET", "Trace underflow");
        }
        return calls.get(calls.size() - 1);
    }

    public void clear() {
        calls.clear();
    }

    public Iterable<Call> calls() {
        ArrayList<Call> calls = new ArrayList<>(this.calls);
        Collections.reverse(calls);
        return calls;
    }

    public static final class Call {

        private final List<String> includes;
        private final String path;
        private final String name;

        public Call(List<String> includes, String path, String name) {
            this.includes = includes;
            this.path = path;
            this.name = name;
        }

        public Iterable<String> includes() {
            return includes;
        }

        public String fullyQualifiedName() {
            return path + "/" + name;
        }
    }
}

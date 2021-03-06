package net.jloop.rejoice.types;

import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;

import java.util.ArrayList;
import java.util.Iterator;

public final class Stack implements Value, Iterable<Value> {

    private final ArrayList<Value> values;

    public Stack() {
        this.values = new ArrayList<>();
    }

    private Stack(ArrayList<Value> values) {
        this.values = values;
    }

    public int length() {
        return values.size();
    }

    public Value get(int i) {
        return values.get(values.size() - i);
    }

    public Value take(int i) {
        return values.remove(values.size() - i);
    }

    private void checkUnderflow() {
        if (values.isEmpty()) {
            throw new RuntimeError("STACK", "Underflow");
        }
    }

    public <V extends Value> V peek(Class<V> type) {
        checkUnderflow();
        Value value = values.get(values.size() - 1);
        if (!type.isInstance(value)) {
            throw new RuntimeError("STACK", "Expecting ^" + type.getSimpleName().toLowerCase() + " but found ^" + value.getClass().getSimpleName().toLowerCase());
        } else {
            return type.cast(value);
        }
    }

    public Value peek() {
        checkUnderflow();
        return values.get(values.size() - 1);
    }

    public <V extends Value> V consume(Class<V> type) {
        V value = peek(type);
        pop();
        return value;
    }

    public Value consume() {
        Value value = peek();
        pop();
        return value;
    }

    public Stack push(Value value) {
        values.add(value);
        return this;
    }

    public Stack pop() {
        checkUnderflow();
        values.remove(values.size() - 1);
        return this;
    }

    @Override
    public String print() {
        StringBuilder buffer = new StringBuilder("[");
        boolean first = true;
        for (Value value : values) {
            if (first) {
                first = false;
            } else {
                buffer.append(" ");
            }
            buffer.append(value.print());
        }
        return buffer.append("]").toString();
    }

    @Override
    public String write() {
        StringBuilder buffer = new StringBuilder();
        boolean first = true;
        for (int i = values.size() - 1; i >= 0; --i) {
            if (first) {
                buffer.append(values.get(i).print()).append(" <-- top");
                first = false;
            } else {
                buffer.append("\n").append(values.get(i).print());
            }
        }
        return buffer.toString();
    }

    public Stack copy() {
        // This is a shallow copy
        return new Stack(new ArrayList<>(values));
    }

    @Override
    public Iterator<Value> iterator() {
        return values.iterator();
    }
}

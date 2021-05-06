package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;

import java.util.ArrayList;
import java.util.Iterator;

public final class Stack implements Value, Iterable<Value> {

    private final ArrayList<Value> values;
    private final boolean onlyAtoms;

    public Stack() {
        this(new ArrayList<>(), false);
    }

    public Stack(boolean onlyAtoms) {
        this(new ArrayList<>(), onlyAtoms);
    }

    private Stack(ArrayList<Value> values, boolean onlyAtoms) {
        this.values = values;
        this.onlyAtoms = onlyAtoms;
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

    public Value peek(int i) {
        return values.get(values.size() - i);
    }

    public <V extends Value> V consume(Class<V> type) {
        V value = peek(type);
        pop();
        return value;
    }

    public Value consume(int i) {
        return values.remove(values.size() - i);
    }

    public Value consume() {
        Value value = peek();
        pop();
        return value;
    }

    public Stack push(Value value) {
        if (onlyAtoms && !(value instanceof Atom)) {
            throw new RuntimeError("STACK", "Cannot push a value onto a stack that only accepts atoms");
        }
        values.add(value);
        return this;
    }

    public Stack push(int i) {
        values.add(values.size() - i, values.remove(values.size() - 1));
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
        return new Stack(new ArrayList<>(values), onlyAtoms);
    }

    @Override
    public Iterator<Value> iterator() {
        return values.iterator();
    }
}

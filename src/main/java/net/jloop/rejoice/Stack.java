package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class Stack implements Iterable<Value> {

    private final ArrayList<Value> values;

    public Stack() {
        this.values = new ArrayList<>();
    }

    private Stack(ArrayList<Value> values) {
        this.values = values;
    }

    public <V extends Value> V peek(Class<V> type) {
        Value value = values.get(values.size() - 1);
        if (!type.isInstance(value)) {
            throw new RuntimeError("STACK", "Expecting " + type.getSimpleName() + " but found " + value.getClass().getSimpleName());
        } else {
            return type.cast(value);
        }
    }

    public <V extends Value> V consume(Class<V> type) {
        V value = peek(type);
        pop();
        return value;
    }

    public Stack push(Value value) {
        values.add(value);
        return this;
    }

    public Stack pop() {
        values.remove(values.size() - 1);
        return this;
    }

    public void print() {
        ArrayList<Value> list = new ArrayList<>(values);
        Collections.reverse(list);
        for (Value value : list) {
            System.out.println(value.print());
        }
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

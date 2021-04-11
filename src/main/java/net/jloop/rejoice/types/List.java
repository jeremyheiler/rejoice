package net.jloop.rejoice.types;

import net.jloop.rejoice.Value;
import net.jloop.rejoice.protocols.Cons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class List implements Value, Cons, Iterable<Value> {

    private final ArrayList<Value> values;

    public List() {
        this.values = new ArrayList<>();
    }

    public List(Value ... values) {
        this.values = new ArrayList<>();
        this.values.addAll(Arrays.asList(values));
    }

    public List(ArrayList<Value> values) {
        this.values = values;
    }

    @Override
    public void cons(Value value) {
        prepend(value);
    }

    public List append(Value value) {
        values.add(value);
        return this;
    }

    public List prepend(Value value) {
        values.add(0, value);
        return this;
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.size() == 0;
    }

    public Value first() {
        if (values.isEmpty()) {
            return null;
        } else {
            return values.get(0);
        }
    }

    public List rest() {
        if (values.isEmpty()) {
            return new List();
        } else {
            ArrayList<Value> values = new ArrayList<>(this.values);
            values.remove(0);
            return new List(values);
        }
    }

    @Override
    public String print() {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        boolean first = true;
        for (Value value : values) {
            if (first) {
                first = false;
            } else {
                buf.append(" ");
            }
            buf.append(value.print());
        }
        buf.append("]");
        return buf.toString();
    }

    @Override
    public Iterator<Value> iterator() {
        return values.iterator();
    }
}

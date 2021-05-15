package net.jloop.rejoice.types;

import net.jloop.rejoice.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Quote implements Value, Iterable<Value> {

    private final List<Value> values;

    public Quote() {
        this.values = new ArrayList<>();
    }

    public Quote(Value... values) {
        this.values = new ArrayList<>();
        this.values.addAll(Arrays.asList(values));
    }

    public Quote(List<Value> values) {
        this.values = values;
    }

    @Override
    public Type type() {
        return Type.List;
    }

    public Quote append(Value value) {
        values.add(value);
        return this;
    }

    public Quote cons(Value value) {
        values.add(0, value);
        return this;
    }

    public int length() {
        return values.size();
    }

    public Value first() {
        if (values.isEmpty()) {
            return null;
        } else {
            return values.get(0);
        }
    }

    public Quote rest() {
        if (values.isEmpty()) {
            return new Quote();
        } else {
            ArrayList<Value> values = new ArrayList<>(this.values);
            values.remove(0);
            return new Quote(values);
        }
    }

    @Override
    public String print() {
        StringBuilder buffer = new StringBuilder().append("(");
        for (Value value : values) {
            buffer.append(" ").append(value.print());
        }
        return buffer.append(" )").toString();
    }

    @Override
    public Iterator<Value> iterator() {
        return values.iterator();
    }
}

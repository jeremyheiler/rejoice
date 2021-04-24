package net.jloop.rejoice.types;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class List implements Function, Value, Iterable<Value> {

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
    public Stack invoke(Context context, Stack stack) {
        for (Value value : values) {
            stack = value.interpret(context, stack);
        }
        return stack;
    }

    public List append(Value value) {
        values.add(value);
        return this;
    }

    public List cons(Value value) {
        values.add(0, value);
        return this;
    }

    public int length() {
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
        StringBuilder buffer = new StringBuilder().append("(");
        boolean first = true;
        for (Value value : values) {
            if (first) {
                first = false;
            } else {
                buffer.append(" ");
            }
            buffer.append(value.print());
        }
        return buffer.append(")").toString();
    }

    @Override
    public Iterator<Value> iterator() {
        return values.iterator();
    }
}

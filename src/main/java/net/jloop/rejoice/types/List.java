package net.jloop.rejoice.types;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class List implements Value, Iterable<Value>, Function {

    private final ArrayList<Value> values;

    public List() {
        this.values = new ArrayList<>();
    }

    public List(Value... values) {
        this.values = new ArrayList<>();
        this.values.addAll(Arrays.asList(values));
    }

    public List(ArrayList<Value> values) {
        this.values = values;
    }

    @Override
    public Type type() {
        return Type.List;
    }

    @Override
    public Stack invoke(Env env, Stack stack) {
        return new Interpreter().interpret(env, stack, values.iterator());
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

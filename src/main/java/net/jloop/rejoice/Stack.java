package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Iterator;

public class Stack implements Iterable<Atom> {

    private final ArrayList<Atom> atoms;

    public Stack() {
        this.atoms = new ArrayList<>();
    }

    public Stack(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    private int lastIndex() {
        return atoms.size() - 1;
    }

    public <V extends Atom> V peek(Class<V> type) {
        Atom atom = atoms.get(lastIndex());
        if (!type.isInstance(atom)) {
            throw new RuntimeException("Expecting type " + type + " but found " + atom.getClass());
        } else {
            return type.cast(atom);
        }
    }

    public <V extends Atom> V consume(Class<V> type) {
        V atom = peek(type);
        this.pop();
        return atom;
    }

    public Stack push(Atom atom) {
        atoms.add(atom);
        return this;
    }

    public Stack pushAll(Stack stack) {
        for (Atom atom : stack) {
            atoms.add(atom);
        }
        return this;
    }

    public Stack pop() {
        atoms.remove(lastIndex());
        return this;
    }

    public void print() {
        for (Atom atom : atoms) {
            System.out.println(atom.toString());
        }
    }

    public Stack copy() {
        // This is a shallow copy
        return new Stack(new ArrayList<>(atoms));
    }

    @Override
    public Iterator<Atom> iterator() {
        return atoms.iterator();
    }
}

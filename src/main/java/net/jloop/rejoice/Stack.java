package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class Stack implements Iterable<Atom> {

    private final ArrayList<Atom> atoms;

    public Stack() {
        this.atoms = new ArrayList<>();
    }

    private Stack(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    public <V extends Atom> V peek(Class<V> type) {
        Atom atom = atoms.get(atoms.size() - 1);
        if (!type.isInstance(atom)) {
            throw new RuntimeError("STACK", "Expecting " + type.getSimpleName() + " but found " + atom.getClass().getSimpleName());
        } else {
            return type.cast(atom);
        }
    }

    public <V extends Atom> V consume(Class<V> type) {
        V atom = peek(type);
        pop();
        return atom;
    }

    public Stack push(Atom atom) {
        atoms.add(atom);
        return this;
    }

    public Stack pop() {
        atoms.remove(atoms.size() - 1);
        return this;
    }

    public void print() {
        ArrayList<Atom> list = new ArrayList<>(atoms);
        Collections.reverse(list);
        for (Atom atom : list) {
            System.out.println(atom.print());
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

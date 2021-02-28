package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Iterator;

// NOTE: This class is immutable.
// For ease of getting started, immutability is implemented as copy on write.
// This class should be reimplemented as a persistent stack.

public class Stack implements Literal, Iterable<Atom> {

    private final ArrayList<Atom> atoms;

    public Stack() {
        this.atoms = new ArrayList<>();
    }

    protected Stack(ArrayList<Atom> atoms) {
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

    public Stack push(Atom atom) {
        ArrayList<Atom> atoms = new ArrayList<>(this.atoms);
        atoms.add(atom);
        return new Stack(atoms);
    }

    public Stack superPush(Atom atom) {
        ArrayList<Atom> atoms = new ArrayList<>(this.atoms);
        atoms.add(0, atom);
        return new Stack(atoms);
    }

    public Stack drop() {
        ArrayList<Atom> atoms = new ArrayList<>(this.atoms);
        atoms.remove(lastIndex());
        return new Stack(atoms);
    }

    public int size() {
        return atoms.size();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        boolean first = true;
        for (Atom atom : atoms) {
            if (first) {
                first = false;
            } else {
                buf.append(" ");
            }
            buf.append(atom);
        }
        buf.append("]");
        return buf.toString();
    }

    public void print() {
        for (Atom atom : atoms) {
            System.out.println(atom.toString());
        }
    }

    @Override
    public Iterator<Atom> iterator() {
        return atoms.iterator();
    }
}

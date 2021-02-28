package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Iterator;

public class List implements Literal, Iterable<Atom> {

    private final ArrayList<Atom> atoms;

    public List() {
        this.atoms = new ArrayList<>();
    }

    public List(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    public void append(Atom atom) {
        atoms.add(atom);
    }

    public int size() {
        return atoms.size();
    }

    public Atom first() {
        return atoms.get(0);
    }

    public List rest() {
        ArrayList<Atom> atoms = new ArrayList<>(this.atoms);
        atoms.remove(0);
        return new List(atoms);
    }

    public Stack unquote(Library library, Stack stack) {
        for (Atom atom : atoms) {
            stack = atom.evaluate(library, stack);
        }
        return stack;
    }

    public Stack toStack() {
        return new Stack(atoms);
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

    @Override
    public Iterator<Atom> iterator() {
        return atoms.iterator();
    }
}

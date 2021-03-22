package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Iterator;

public class List implements Atom, Iterable<Atom> {

    private final ArrayList<Atom> atoms;

    public List() {
        this.atoms = new ArrayList<>();
    }

    public List(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    public static List from(Stack stack) {
        List list = new List();
        for (Atom atom : stack) {
            list.append(atom);
        }
        return list;
    }

    public void append(Atom atom) {
        atoms.add(atom);
    }

    public int size() {
        return atoms.size();
    }

    public Atom first() {
        if (atoms.isEmpty()) {
            return null;
        } else {
            return atoms.get(0);
        }
    }

    public List rest() {
        if (atoms.isEmpty()) {
            return new List();
        } else {
            ArrayList<Atom> atoms = new ArrayList<>(this.atoms);
            atoms.remove(0);
            return new List(atoms);
        }
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

package net.jloop.rejoice;

import java.util.ArrayList;

public class Program {

    private final ArrayList<Atom> atoms;

    public Program(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    public Stack evaluate(Library library, Stack stack) {
        Stack s = stack;
        for (Atom atom : atoms) {
            s = atom.evaluate(library, s);
        }
        return s;
    }
}

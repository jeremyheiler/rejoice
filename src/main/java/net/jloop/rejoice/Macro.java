package net.jloop.rejoice;

import java.util.Iterator;

@FunctionalInterface
public interface Macro {

    Iterable<Value> evaluate(Compiler compiler, Iterator<Atom> atoms);
}

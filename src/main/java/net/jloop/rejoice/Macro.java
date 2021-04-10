package net.jloop.rejoice;

import java.util.Iterator;

@FunctionalInterface
public interface Macro {

    Iterable<Atom> rewrite(Rewriter rewriter, Iterator<Atom> input);
}

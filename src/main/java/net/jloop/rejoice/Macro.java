package net.jloop.rejoice;

import java.util.Iterator;

@FunctionalInterface
public interface Macro {

    Iterator<Atom> rewrite(Rewriter rewriter, Iterator<Atom> atoms);
}

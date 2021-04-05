package net.jloop.rejoice;

import java.util.Iterator;
import java.util.List;

@FunctionalInterface
public interface Macro {

    List<Atom> rewrite(Rewriter rewriter, Iterator<Atom> iterator);
}

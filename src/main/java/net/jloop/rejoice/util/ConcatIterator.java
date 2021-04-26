package net.jloop.rejoice.util;

import java.util.Iterator;

public final class ConcatIterator<E> implements Iterator<E> {

    private final Iterator<E> alpha;
    private final Iterator<E> bravo;

    public ConcatIterator(Iterator<E> alpha, Iterator<E> bravo) {
        this.alpha = alpha;
        this.bravo = bravo;
    }

    @Override
    public boolean hasNext() {
        return alpha.hasNext() || bravo.hasNext();
    }

    @Override
    public E next() {
        return alpha.hasNext() ? alpha.next() : bravo.next();
    }
}

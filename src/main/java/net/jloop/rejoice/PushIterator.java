package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class PushIterator<E> implements Iterator<E> {

    private final List<E> front = new ArrayList<>();
    private final Iterator<E> iterator;

    public PushIterator(Iterator<E> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }

    public PushIterator<E> push(E element) {
        front.add(element);
        return this;
    }

    public PushIterator<E> push(List<E> list) {
        for (int i = list.size() - 1; i >= 0; --i) {
            front.add(list.get(i));
        }
        return this;
    }

    @Override
    public boolean hasNext() {
        return !front.isEmpty() || iterator.hasNext();
    }

    @Override
    public E next() {
        return front.isEmpty() ? iterator.next() : front.remove(front.size() - 1);
    }
}

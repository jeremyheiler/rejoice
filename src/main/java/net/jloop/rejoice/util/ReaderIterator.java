package net.jloop.rejoice.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class ReaderIterator implements Iterator<Character> {

    private final Reader reader;

    private boolean advanced;
    private int next;

    public ReaderIterator(Reader reader) {
        this.reader = Objects.requireNonNull(reader);
    }

    private void advance() {
        try {
            next = reader.read();
        } catch (IOException e) {
            Exceptions.sneakyThrow(e);
        }
    }

    @Override
    public boolean hasNext() {
        if (!advanced) {
            advanced = true;
            advance();
        }
        return next != -1;
    }

    @Override
    public Character next() {
        if (advanced) {
            advanced = false;
        } else {
            advance();
        }
        if (next == -1) {
            throw new NoSuchElementException();
        } else {
            return (char) next;
        }
    }
}

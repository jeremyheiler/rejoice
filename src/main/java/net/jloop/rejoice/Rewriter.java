package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Rewriter {

    private final Context context;

    public Rewriter(Context context) {
        this.context = context;
    }

    private final class Iterate implements Iterator<Atom> {

        private final ArrayList<Atom> rewritten = new ArrayList<>();
        private Iterator<Atom> input;

        public Iterate(Iterator<Atom> input) {
            this.input = input;
        }

        private void rewrite() {
            input = input.next().rewrite(context, rewritten, input);
        }

        @Override
        public boolean hasNext() {
            if (rewritten.isEmpty()) {
                if (input.hasNext()) {
                    rewrite();
                    return hasNext();
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }

        @Override
        public Atom next() {
            if (rewritten.isEmpty()) {
                if (input.hasNext()) {
                    rewrite();
                    return next();
                } else {
                    throw new NoSuchElementException();
                }
            } else {
                return rewritten.remove(0);
            }
        }
    }

    public Iterator<Atom> map(Iterator<Atom> input) {
        return new Iterate(input);
    }
}

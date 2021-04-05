package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.PushIterator;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class M_Libra implements Macro {

    private final M_Define define;
    private final M_MultilineComment comment;
    private final Symbol terminator;

    public M_Libra(M_Define define, M_MultilineComment comment, Symbol terminator) {
        this.define = define;
        this.comment = comment;
        this.terminator = terminator;
    }

    @Override
    public List<Atom> rewrite(Rewriter rewriter, Iterator<Atom> it) {
        PushIterator<Atom> iterator = new PushIterator<>(it);
        ArrayList<Atom> atoms = new ArrayList<>();
        while (iterator.hasNext()) {
            Atom atom = iterator.next();
            if (atom.equals(Symbol.of("HIDE"))) {
                throw new RuntimeError("MACRO", "HIDE not yet implemented");
            } else if (atom.equals(comment.dispatcher())) {
                comment.rewrite(rewriter, iterator);
            } else {
                iterator.push(atom);
                AtomicBoolean done = new AtomicBoolean(false);
                atoms.addAll(define.rewrite(rewriter, iterator, terminator, terminator -> {
                    if (terminator.equals(M_Libra.this.terminator)) {
                        done.set(true);
                    }
                }));
                if (done.get()) {
                    atoms.add(terminator);
                    break;
                }
            }
        }
        return atoms;
    }
}

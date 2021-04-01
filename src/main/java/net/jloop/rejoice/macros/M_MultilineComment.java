package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Compiler;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;

public class M_MultilineComment implements Macro {

    private final Symbol terminator;

    public M_MultilineComment(Symbol terminator) {
        this.terminator = terminator;
    }

    @Override
    public Iterable<Value> evaluate(Compiler compiler, Iterator<Atom> atoms) {
        boolean terminated = false;
        ArrayList<Value> values = new ArrayList<>();
        while (atoms.hasNext()) {
            for (Value value : compiler.compile(atoms.next(), atoms)) {
                if (terminated) {
                    values.add(value);
                } else if (value.equals(terminator)) {
                    terminated = true;
                }
            }
        }
        if (terminated) {
            return values;
        } else {
            throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete multi-line comment");
        }
    }
}

package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Compiler;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;

public final class M_List implements Macro {

    private final Symbol terminator;

    public M_List(Symbol terminator) {
        this.terminator = terminator;
    }

    @Override
    public Iterable<Value> evaluate(Compiler compiler, Iterator<Atom> atoms) {
        List list = new List();
        boolean terminated = false;
        ArrayList<Value> values = new ArrayList<>();
        while (atoms.hasNext()) {
            for (Value value : compiler.compile(atoms.next(), atoms)) {
                if (terminated) {
                    values.add(value);
                } else if (value.equals(terminator)) {
                    terminated = true;
                    values.add(list);
                } else {
                    list.append(value);
                }
            }
        }
        if (terminated) {
            return values;
        } else {
            throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete list");
        }
    }
}

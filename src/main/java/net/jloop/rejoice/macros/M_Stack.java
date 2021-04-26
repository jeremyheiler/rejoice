package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class M_Stack implements Macro {

    @Override
    public Iterator<Atom> rewrite(Context context, Iterator<Atom> input) {
        List<Atom> output = new ArrayList<>();
        for (Atom atom : Macro.collect(context, input, Symbol.of("]"))) {
            output.add(atom.quote());
        }
        Collections.reverse(output);
        output.add(new Int64(output.size()));
        output.add(Symbol.of("stack"));
        return output.iterator();
    }
}

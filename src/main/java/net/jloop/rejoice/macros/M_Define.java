package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.util.ConcatIterator;

import java.util.ArrayList;
import java.util.Iterator;

public final class M_Define implements Macro {

    @Override
    public Iterator<Atom> rewrite(Context context, Iterator<Atom> input) {
        Symbol name = Macro.match(input, Symbol.class);
        Macro.match(input, Symbol.of(":"));
        ArrayList<Atom> output = new ArrayList<>();
        output.add(Symbol.of("("));
        output.addAll(Macro.collect(context, input, Symbol.of(";")));
        output.add(Symbol.of(")"));
        output.add(name.quote());
        output.add(Symbol.of("define!"));
        return new ConcatIterator<>(output.iterator(), input);
    }
}

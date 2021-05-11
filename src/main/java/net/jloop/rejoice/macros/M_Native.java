package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Symbol;

import java.util.Collections;
import java.util.Iterator;

public final class M_Native implements Macro {

    @Override
    public Iterator<Value> invoke(Env env, Iterator<Value> input) {
        MacroHelper.match(input, Symbol.class);
        MacroHelper.match(input, Symbol.of(";"));
        return Collections.emptyIterator();
    }
}

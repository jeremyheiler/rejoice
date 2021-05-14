package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class M_quote implements Macro {

    @Override
    public Iterator<Value> call(Env env, Iterator<Value> input) {
        List<Value> list = MacroHelper.collect(env, input, Symbol.of(")"));
        return Collections.<Value>singletonList(new Quote(list)).iterator();
    }
}

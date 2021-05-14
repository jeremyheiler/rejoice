package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class M_function implements Macro {

    @Override
    public Iterator<Value> call(Env env, Iterator<Value> input) {
        Symbol name = MacroHelper.match(input, Type.Symbol);
        MacroHelper.match(input, Symbol.of(":"));
        List<Value> body = MacroHelper.collect(env, input, Symbol.of(";"));
        env.define(name, new Quote(body));
        return Collections.emptyIterator();
    }
}

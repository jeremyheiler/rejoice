package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Protocol;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.Collections;
import java.util.Iterator;

public final class M_protocol implements Macro {

    @Override
    public Iterator<Value> call(Env env, Iterator<Value> input) {
        Symbol name = MacroHelper.match(input, Type.Symbol);
        MacroHelper.match(input, Symbol.of(";"));
        env.define(name, new Protocol(name));
        return Collections.emptyIterator();
    }
}

package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Protocol;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class M_extend implements Macro {

    @Override
    public Iterator<Value> call(Env env, Iterator<Value> input) {
        Symbol name = MacroHelper.match(input, Type.Symbol);
        Type type = MacroHelper.match(input, Type.Type);
        MacroHelper.match(input, Symbol.of(":"));
        List<Value> body = MacroHelper.collect(env, input, Symbol.of(";"));
        Function function = env.lookup(name);
        if (!(function instanceof Protocol)) {
            throw new RuntimeError("INTERPRET", "No protocol with the name '" + name.name() + "'");
        }
        Protocol protocol = (Protocol) function;
        protocol.extend(type, new Quote(body));
        return Collections.emptyIterator();
    }
}

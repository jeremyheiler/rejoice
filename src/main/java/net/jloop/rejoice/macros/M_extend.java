package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class M_extend implements Macro {

    @Override
    public Iterator<Value> invoke(Env env, Iterator<Value> input) {
        // Pattern
        Symbol name = MacroHelper.match(input, Type.Symbol);
        Type type = MacroHelper.match(input, Type.Type);
        MacroHelper.match(input, Symbol.of(":"));
        List<Value> body = MacroHelper.collect(env, input, Symbol.of(";"));
        // Template
        List<Value> output = new ArrayList<>();
        output.add(Symbol.of("("));
        output.addAll(body);
        output.add(Symbol.of(")"));
        output.add(name.quote());
        output.add(type);
        output.add(Symbol.of("extend-protocol"));
        return output.iterator();
    }
}

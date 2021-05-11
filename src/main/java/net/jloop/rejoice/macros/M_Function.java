package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class M_Function implements Macro {

    @Override
    public Iterator<Value> invoke(Env env, Iterator<Value> input) {
        // Pattern
        Symbol name = MacroHelper.match(input, Symbol.class);
        MacroHelper.match(input, Symbol.of(":"));
        List<Value> body = MacroHelper.collect(env, input, Symbol.of(";"));
        // Template
        List<Value> output = new ArrayList<>();
        output.add(Symbol.of("("));
        output.addAll(body);
        output.add(Symbol.of(")"));
        output.add(name.quote());
        output.add(Symbol.of("define-function"));
        return output.iterator();
    }
}

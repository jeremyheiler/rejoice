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

public final class M_protocol implements Macro {

    @Override
    public Iterator<Value> invoke(Env env, Iterator<Value> input) {
        // Pattern
        Symbol name = MacroHelper.match(input, Type.Symbol);
        MacroHelper.match(input, Symbol.of(";"));
        // Template
        List<Value> output = new ArrayList<>();
        output.add(name.quote());
        output.add(Symbol.of("define-protocol"));
        return output.iterator();
    }
}
